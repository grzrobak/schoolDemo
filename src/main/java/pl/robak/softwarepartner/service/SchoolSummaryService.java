package pl.robak.softwarepartner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.robak.softwarepartner.Configuration;
import pl.robak.softwarepartner.model.db.Attendance;
import pl.robak.softwarepartner.model.db.School;
import pl.robak.softwarepartner.model.summary.SchoolSummary;
import pl.robak.softwarepartner.repository.AttendanceRepository;
import pl.robak.softwarepartner.repository.ChildRepository;
import pl.robak.softwarepartner.repository.SchoolRepository;

import java.time.*;
import java.util.List;

@Service
public class SchoolSummaryService {

    private final Configuration configuration;

    private final SchoolRepository schoolRepository;

    private final AttendanceRepository attendanceRepository;

    @Autowired
    public SchoolSummaryService(Configuration configuration, SchoolRepository schoolRepository, AttendanceRepository attendanceRepository) {
        this.configuration = configuration;
        this.schoolRepository = schoolRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public SchoolSummary createSchoolSummary(long id, int year, int month) {
        School school = schoolRepository.findById(id);

        ZonedDateTime startDate = findStartDate(year, month);
        ZonedDateTime endDate = findEndDate(year, month);
        List<Attendance> attendances = attendanceRepository.findAllBetween(startDate, endDate, id);



        return new SchoolSummary(attendances);
    }

    private ZonedDateTime findStartDate(int year, int month) {
        return LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    }

    private ZonedDateTime findEndDate(int year, int month) {
        return LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC);
    }

}
