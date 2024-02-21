package pl.robak.softwarepartner.model.summary;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record SchoolSummary(List<ParentSummary> parents, BigDecimal hour_price) {

    @JsonProperty("total")
    public BigDecimal total() {
        return parents.stream()
                .map(ParentSummary::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getPaidTimeInHours() {
        return parents.stream()
                .mapToInt(ParentSummary::getPaidTimeInHours)
                .sum();
    }

}
