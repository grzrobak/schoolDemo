package pl.robak.softwarepartner.model.db;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date entry_date;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date exit_date;

    public Attendance() {
    }

    public Attendance(Long id, Child child, Date entry_date, Date exit_date) {
        this.id = id;
        this.child = child;
        this.entry_date = entry_date;
        this.exit_date = exit_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public Date getExit_date() {
        return exit_date;
    }

    public void setExit_date(Date exit_date) {
        this.exit_date = exit_date;
    }
}
