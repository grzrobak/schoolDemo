package pl.robak.softwarepartner.model.summary;

import java.math.BigDecimal;
import java.util.List;

public record ParentSummary(String firstName, String lastName, List<ChildSummary> children) {

    public BigDecimal getTotal() {
        return children.stream()
                .map(ChildSummary::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getPaidTimeInHours() {
        return children.stream()
                .mapToInt(ChildSummary::getPaidTimeInHours)
                .sum();
    }
}
