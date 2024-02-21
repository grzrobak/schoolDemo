package pl.robak.softwarepartner.model.summary;

import pl.robak.softwarepartner.model.db.Attendance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

public class AttendanceRecord implements Summary {

    public final ZonedDateTime entry_date;

    public final ZonedDateTime exit_date;

    private final transient OffsetTime freeTimeStart;

    private final transient OffsetTime freeTimeEnd;

    private final transient BigDecimal schoolRate;

    public AttendanceRecord(ZonedDateTime entry_date, ZonedDateTime exit_date, OffsetTime freeTimeStart, OffsetTime freeTimeEnd, BigDecimal schoolRate) {
        this.entry_date = entry_date;
        this.exit_date = exit_date;
        this.freeTimeStart = freeTimeStart;
        this.freeTimeEnd = freeTimeEnd;
        this.schoolRate = schoolRate;
    }

    public AttendanceRecord(Attendance attendance, OffsetTime freeTimeStart, OffsetTime freeTimeEnd, BigDecimal schoolRate) {
        this(attendance.getEntry_date(), attendance.getExit_date(), freeTimeStart, freeTimeEnd, schoolRate);
    }

    @Override
    public int getTotalHours() {
        int entryHour = entry_date.getHour();
        int exitHour = exit_date.getHour();

        if(entry_date.isAfter(exit_date)) {
            Logger.getLogger(this.getClass().getName()).warning("Data inconsistency: entry_date is after exit_date");
            return 0;
        }

        int additionalHour = exit_date.getMinute() > 0 ? 1 : 0;

        return exitHour - entryHour + additionalHour;
    }

    @Override
    public int getPaidTimeInHours() {
        int entryHour = entry_date.getHour();
        int exitHour = exit_date.getHour();

        if(entry_date.isAfter(exit_date)) {
            Logger.getLogger(this.getClass().getName()).warning("Data inconsistency: entry_date is after exit_date");
            return 0;
        }

        int freeTimeStartHour = freeTimeStart.getHour();
        int freeTimeEndHour = freeTimeEnd.getHour();

        int additionalHour = exit_date.getMinute() > 0 && exitHour > freeTimeEndHour ? 1 : 0;

        if (entry_date.isAfter(entry_date.with(freeTimeEnd))) {
            return exitHour - entryHour + additionalHour;
        }

        return Math.max(freeTimeStartHour - entryHour, 0) + Math.max(exitHour - freeTimeEndHour, 0) + additionalHour;
    }

    @Override
    public BigDecimal getPaymentTotal() {
        return schoolRate.multiply(BigDecimal.valueOf(getPaidTimeInHours())).setScale(2, RoundingMode.HALF_UP);
    }

}
