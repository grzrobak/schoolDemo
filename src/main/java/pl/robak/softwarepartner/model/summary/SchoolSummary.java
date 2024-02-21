package pl.robak.softwarepartner.model.summary;

import java.math.BigDecimal;

public record SchoolSummary(Summaries<ParentSummary> parents, BigDecimal hour_price) implements Summary {

    @Override
    public BigDecimal getPaymentTotal() {
        return parents.getPaymentTotal();
    }

    @Override
    public int getPaidTimeInHours() {
        return parents.getPaidTimeInHours();
    }

    @Override
    public int getTotalHours() {
        return parents.getTotalHours();
    }

}
