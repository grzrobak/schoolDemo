package pl.robak.softwarepartner.model.summary;

import pl.robak.softwarepartner.model.db.Child;

import java.math.BigDecimal;

public record ChildSummary(String firstName, String lastName, Summaries<AttendanceRecord> attendances) implements Summary {

    public ChildSummary(Child child, Summaries<AttendanceRecord> attendances) {
        this(child.getFirstname(), child.getLastname(), attendances);
    }

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
