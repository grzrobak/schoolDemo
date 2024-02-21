package pl.robak.softwarepartner.model.summary;

import java.math.BigDecimal;

public record ParentSummary(String firstName, String lastName, Summaries<ChildSummary> children) implements Summary {

    @Override
    public BigDecimal getPaymentTotal() {
        return children.getPaymentTotal();
    }

    @Override
    public int getPaidTimeInHours() {
        return children.getPaidTimeInHours();
    }

    @Override
    public int getTotalHours() {
        return children.getTotalHours();
    }
}
