package pl.robak.softwarepartner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.robak.softwarepartner.Configuration;
import pl.robak.softwarepartner.model.db.Attendance;
import pl.robak.softwarepartner.model.db.Child;
import pl.robak.softwarepartner.model.db.Parent;
import pl.robak.softwarepartner.model.db.School;
import pl.robak.softwarepartner.model.summary.*;
import pl.robak.softwarepartner.repository.AttendanceRepository;
import pl.robak.softwarepartner.repository.ParentRepository;
import pl.robak.softwarepartner.repository.SchoolRepository;
import pl.robak.softwarepartner.rest.error.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ParentSummaryService extends SummaryService {

    private final AttendanceRepository attendanceRepository;

    private final ParentRepository parentRepository;

    @Autowired
    public ParentSummaryService(Configuration config, AttendanceRepository attendanceRepository, ParentRepository parentRepository) {
        super(config);
        this.attendanceRepository = attendanceRepository;
        this.parentRepository = parentRepository;
    }

    public ParentSummary createParentSummary(long id, int year, int month) {
        Parent parent = parentRepository.findById(id);

        if (parent == null) {
            throw new ResourceNotFoundException("Parent not found");
        }

        ZonedDateTime startDate = findStartDate(year, month);
        ZonedDateTime endDate = findEndDate(year, month);
        List<Attendance> attendances = attendanceRepository.findAllBetweenStartDateAndEndDateFilterByParent(startDate, endDate, id);

        Map<Child, List<Attendance>> childrenAttendances = attendances.stream().collect(Collectors.groupingBy(Attendance::getChild));

        return new ParentSummary(parent.getFirstname(), parent.getLastname(), new Summaries<>(childrenAttendances.keySet().stream()
                .map(child -> new ChildSummary(child.getFirstname(), child.getLastname(),
                        new Summaries<>(childrenAttendances.get(child).stream()
                                .filter(a -> a.getChild().getId().equals(child.getId()))
                                .map(createAttendanceDto(child.getSchool().getHour_price()))
                                .toList())))
                .toList()));
    }

}
