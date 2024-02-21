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
import pl.robak.softwarepartner.repository.SchoolRepository;
import pl.robak.softwarepartner.rest.error.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SchoolSummaryService {

    private final Configuration config;

    private final SchoolRepository schoolRepository;

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public SchoolSummaryService(Configuration config, SchoolRepository schoolRepository, AttendanceRepository attendanceRepository) {
        this.config = config;
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
        List<Attendance> attendances = attendanceRepository.findAllBetween(startDate, endDate, id);

        Map<Child, List<Attendance>> childrenAttendances = attendances.stream().collect(Collectors.groupingBy(Attendance::getChild));

        Map<Parent, List<Child>> parentChild = childrenAttendances.keySet().stream().collect(Collectors.groupingBy(Child::getParent));

        Summaries<ParentSummary> parentSummaries = new Summaries<>(
                parentChild.keySet().stream()
                    .map(parent -> new ParentSummary(parent.getFirstname(), parent.getLastname(),
                            new Summaries<>(parentChild.get(parent).stream()
                                .map(child -> new ChildSummary(child.getFirstname(), child.getLastname(),
                                        new Summaries<>(attendances.stream()
                                            .filter(a -> a.getChild().getId().equals(child.getId()))
                                            .map(createAttendanceDto(school.getHour_price()))
                                            .toList())))
                        .toList())))
                .toList());

        return new SchoolSummary(parentSummaries, school.getHour_price());
    }

    private Function<Attendance, AttendanceRecord> createAttendanceDto(BigDecimal schoolRate) {
        return a -> new AttendanceRecord(a, config.getFreeTimeStart(), config.getFreeTimeEnd(), schoolRate);
    }

    private ZonedDateTime findStartDate(int year, int month) {
        return LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    }

    private ZonedDateTime findEndDate(int year, int month) {
        return LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC);
    }

}
