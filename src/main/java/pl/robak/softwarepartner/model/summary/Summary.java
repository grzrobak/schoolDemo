package pl.robak.softwarepartner.model.summary;

import java.math.BigDecimal;

public interface Summary {
    int getTotalHours();

    BigDecimal getPaymentTotal();

    int getPaidTimeInHours();
}
