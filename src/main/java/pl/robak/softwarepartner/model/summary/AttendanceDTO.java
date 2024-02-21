package pl.robak.softwarepartner.model.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.robak.softwarepartner.model.db.Attendance;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class AttendanceDTO {

    public final ZonedDateTime entry_date;

    public final ZonedDateTime exit_date;

    private final transient OffsetTime freeTimeStart;
    private final transient OffsetTime freeTimeEnd;

    private final transient BigDecimal schoolRate;

    public AttendanceDTO(ZonedDateTime entry_date, ZonedDateTime exit_date, OffsetTime freeTimeStart, OffsetTime freeTimeEnd, BigDecimal schoolRate) {
        this.entry_date = entry_date;
        this.exit_date = exit_date;
        this.freeTimeStart = freeTimeStart;
        this.freeTimeEnd = freeTimeEnd;
        this.schoolRate = schoolRate;
    }

    public AttendanceDTO(Attendance attendance, OffsetTime freeTimeStart, OffsetTime freeTimeEnd, BigDecimal schoolRate) {
        this.entry_date = attendance.getEntry_date();
        this.exit_date = attendance.getExit_date();
        this.freeTimeStart = freeTimeStart;
        this.freeTimeEnd = freeTimeEnd;
        this.schoolRate = schoolRate;
    }

    public int getPaidTimeInHours() {
        int entryHour = entry_date.getHour();
        int exitHour = exit_date.getHour();

        int freeTimeStartHour = freeTimeStart.getHour();
        int freeTimeEndHour = freeTimeEnd.getHour();

        int additionalHour = exit_date.getMinute() > 0 && exitHour > freeTimeEndHour ? 1 : 0;

        int paidHours = Math.max(freeTimeStartHour - entryHour, 0) + Math.max(exitHour - freeTimeEndHour, 0) + additionalHour;
        return Math.max(paidHours, 0);
    }

    public BigDecimal getTotal() {
        return schoolRate.multiply(BigDecimal.valueOf(getPaidTimeInHours()));
    }


}
