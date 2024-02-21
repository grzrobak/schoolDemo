package pl.robak.softwarepartner.model.summary;

import pl.robak.softwarepartner.model.db.Attendance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class AttendanceRecord implements Summary {

    public final ZonedDateTime entry_date;

    public final ZonedDateTime exit_date;

    private final transient OffsetTime freeTimeStart;
    private final transient OffsetTime freeTimeEnd;

    private final transient BigDecimal schoolRate;

    public AttendanceRecord(Attendance attendance, OffsetTime freeTimeStart, OffsetTime freeTimeEnd, BigDecimal schoolRate) {
        this.entry_date = attendance.getEntry_date();
        this.exit_date = attendance.getExit_date();
        this.freeTimeStart = freeTimeStart;
        this.freeTimeEnd = freeTimeEnd;
        this.schoolRate = schoolRate;
    }

    public int getTotalHours() {
        int entryHour = entry_date.getHour();
        int exitHour = exit_date.getHour();

        int freeTimeEndHour = freeTimeEnd.getHour();

        int additionalHour = exit_date.getMinute() > 0 && exitHour > freeTimeEndHour ? 1 : 0;
        return exitHour - entryHour + additionalHour;
    }

    public int getPaidTimeInHours() {
        int entryHour = entry_date.getHour();
        int exitHour = exit_date.getHour();

        int freeTimeStartHour = freeTimeStart.getHour();
        int freeTimeEndHour = freeTimeEnd.getHour();

        int additionalHour = exit_date.getMinute() > 0 && exitHour > freeTimeEndHour ? 1 : 0;

        return Math.max(freeTimeStartHour - entryHour, 0) + Math.max(exitHour - freeTimeEndHour, 0) + additionalHour;
    }

    public BigDecimal getPaymentTotal() {
        return schoolRate.multiply(BigDecimal.valueOf(getPaidTimeInHours())).setScale(2, RoundingMode.HALF_UP);
    }


}
