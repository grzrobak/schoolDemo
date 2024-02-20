package pl.robak.softwarepartner.model.db;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private BigDecimal hour_price;

    @OneToMany(mappedBy = "school")
    private List<Child> children;

    public School() {
    }

    public School(Long id, String name, BigDecimal hour_price) {
        this.id = id;
        this.name = name;
        this.hour_price = hour_price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getHour_price() {
        return hour_price;
    }

    public void setHour_price(BigDecimal hour_price) {
        this.hour_price = hour_price;
    }
}
