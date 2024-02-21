package pl.robak.softwarepartner.model.summary;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.robak.softwarepartner.TestUtils.*;

class SummariesTest {

    @Test
    void getPaidTimeInHours() {
        Summaries<AttendanceRecord> attendanceRecords = new Summaries<>(
                List.of(
                        newAttendance(createZonedDateTime(6, 0), createZonedDateTime(14, 0)),
                        newAttendance(createZonedDateTime(6, 0), createZonedDateTime(11, 0))
                )
        );
        assertThat(attendanceRecords.getPaidTimeInHours()).isEqualTo(4);
    }

    @Test
    void getTotalHours() {
        Summaries<AttendanceRecord> attendanceRecords = new Summaries<>(
                List.of(
                        newAttendance(createZonedDateTime(6, 0), createZonedDateTime(14, 0)),
                        newAttendance(createZonedDateTime(6, 0), createZonedDateTime(11, 0))
                )
        );
        assertThat(attendanceRecords.getTotalHours()).isEqualTo(13);
    }

    @Test
    void getPaymentTotal() {
        Summaries<AttendanceRecord> attendanceRecords = new Summaries<>(
                List.of(
                        newAttendance(createZonedDateTime(6, 0), createZonedDateTime(14, 0)),
                        newAttendance(createZonedDateTime(6, 0), createZonedDateTime(11, 0))
                )
        );
        assertThat(attendanceRecords.getPaymentTotal()).isEqualTo(createPaymentValue("40"));
    }
}