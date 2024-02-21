package pl.robak.softwarepartner.model.summary;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record ChildSummary(String firstName, String lastName, List<AttendanceDTO> attendances) {

    public BigDecimal getTotal() {
        return attendances.stream()
                .map(AttendanceDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getPaidTimeInHours() {
        return attendances.stream()
                .mapToInt(AttendanceDTO::getPaidTimeInHours)
                .sum();
    }

}
