package pl.robak.softwarepartner.model.summary;

import pl.robak.softwarepartner.model.db.Parent;

import java.math.BigDecimal;

public record ParentSummary(String firstName, String lastName, Summaries<ChildSummary> children) implements Summary {

    public ParentSummary(Parent parent, Summaries<ChildSummary> children) {
        this(parent.getFirstname(), parent.getLastname(), children);
    }

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
