package pl.robak.softwarepartner.model.summary;

import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.List;

public class Summaries<T extends Summary> extends AbstractList<T> implements Summary {

    private final List<T> list;

    public Summaries(List<T> list) {
        this.list = list;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }


    @Override
    public int getPaidTimeInHours() {
        return list.stream()
                .mapToInt(Summary::getPaidTimeInHours)
                .sum();
    }

    @Override
    public int getTotalHours() {
        return list.stream()
                .mapToInt(Summary::getTotalHours)
                .sum();
    }

    @Override
    public BigDecimal getPaymentTotal() {
        return list.stream()
                .map(Summary::getPaymentTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
