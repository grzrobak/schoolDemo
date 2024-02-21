package pl.robak.softwarepartner.service;

import pl.robak.softwarepartner.Configuration;
import pl.robak.softwarepartner.model.db.Attendance;
import pl.robak.softwarepartner.model.summary.AttendanceRecord;

import java.math.BigDecimal;
import java.time.*;
import java.util.function.Function;

public class SummaryService {

    private final Configuration config;

    public SummaryService(Configuration config) {
        this.config = config;
    }

    protected final ZonedDateTime findStartDate(int year, int month) {
        return LocalDate.of(year, month, 1).atStartOfDay().atZone(ZoneOffset.UTC);
    }

    protected final ZonedDateTime findEndDate(int year, int month) {
        return LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()).atTime(LocalTime.MAX).atZone(ZoneOffset.UTC);
    }

    protected final Function<Attendance, AttendanceRecord> createAttendanceDto(BigDecimal schoolRate) {
        return a -> new AttendanceRecord(a, config.getFreeTimeStart(), config.getFreeTimeEnd(), schoolRate);
    }
}
