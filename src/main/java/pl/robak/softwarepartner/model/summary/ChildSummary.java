package pl.robak.softwarepartner.model.summary;

import java.math.BigDecimal;
import java.util.List;

public record ChildSummary(String firstName, String lastName, Summaries<AttendanceRecord> attendances) implements Summary {

    @Override
    public BigDecimal getPaymentTotal() {
        return attendances.getPaymentTotal();
    }

    @Override
    public int getPaidTimeInHours() {
        return attendances.getPaidTimeInHours();
    }

    @Override
    public int getTotalHours() {
        return attendances.getTotalHours();
    }

}
