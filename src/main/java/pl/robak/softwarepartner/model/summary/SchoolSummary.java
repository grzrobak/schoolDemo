package pl.robak.softwarepartner.model.summary;

import pl.robak.softwarepartner.model.db.Attendance;

import java.util.Collections;
import java.util.List;

public class SchoolSummary {

    public final List<Attendance> attendances;

    public SchoolSummary(List<Attendance> attendances) {
        this.attendances = Collections.unmodifiableList(attendances);
    }
}
