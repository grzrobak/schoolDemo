package pl.robak.softwarepartner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.robak.softwarepartner.Configuration;
import pl.robak.softwarepartner.model.db.Attendance;
import pl.robak.softwarepartner.model.db.Child;
import pl.robak.softwarepartner.model.db.Parent;
import pl.robak.softwarepartner.model.db.School;
import pl.robak.softwarepartner.model.summary.ChildSummary;
import pl.robak.softwarepartner.model.summary.ParentSummary;
import pl.robak.softwarepartner.model.summary.SchoolSummary;
import pl.robak.softwarepartner.model.summary.Summaries;
import pl.robak.softwarepartner.repository.AttendanceRepository;
import pl.robak.softwarepartner.repository.SchoolRepository;
import pl.robak.softwarepartner.rest.error.ResourceNotFoundException;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchoolSummaryService extends SummaryService{

    private final SchoolRepository schoolRepository;

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public SchoolSummaryService(Configuration config, SchoolRepository schoolRepository, AttendanceRepository attendanceRepository) {
        super(config);
        this.schoolRepository = schoolRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public SchoolSummary createSchoolSummary(long id, int year, int month) {
        School school = schoolRepository.findById(id);

        if (school == null) {
            throw new ResourceNotFoundException("School not found");
        }

        ZonedDateTime startDate = findStartDate(year, month);
        ZonedDateTime endDate = findEndDate(year, month);

        Map<Child, List<Attendance>> childrenAttendances = attendanceRepository.findAllBetweenStartDateAndEndDateFilterBySchool(startDate, endDate, id)
                .stream()
                .collect(Collectors.groupingBy(Attendance::getChild));

        Map<Parent, List<Child>> parentChild = childrenAttendances.keySet()
                .stream()
                .collect(Collectors.groupingBy(Child::getParent));

        Summaries<ParentSummary> parentSummaries = new Summaries<>(
                parentChild.keySet().stream()
                    .map(parent -> new ParentSummary(parent,
                            new Summaries<>(parentChild.get(parent).stream()
                                .map(child -> new ChildSummary(child,
                                        new Summaries<>(childrenAttendances.get(child).stream()
                                            .map(createAttendanceRecord(school.getHour_price()))
                                            .sorted(Comparator.comparing(a -> a.entry_date))
                                            .toList())))
                        .sorted(Comparator.comparing(ChildSummary::firstName))
                        .toList())))
                .sorted(Comparator.comparing(ParentSummary::lastName))
                .toList());

        return new SchoolSummary(parentSummaries, school.getHour_price());
    }

}
