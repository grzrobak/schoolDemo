package pl.robak.softwarepartner.model.summary;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.robak.softwarepartner.TestUtils.createPaymentValue;
import static pl.robak.softwarepartner.TestUtils.createZonedDateTime;

class AttendanceRecordTest {

    private final OffsetTime freeTimeStart = OffsetTime.of(7, 0, 0, 0, ZoneOffset.UTC);
    private final OffsetTime freeTimeEnd = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);

    private final BigDecimal hour_rate = BigDecimal.TEN;
    @ParameterizedTest
    @MethodSource("getTotalHoursParamProvider")
    void getTotalHours(ZonedDateTime entry_date, ZonedDateTime exit_date, int expected) {
        AttendanceRecord testee = new AttendanceRecord(entry_date, exit_date, freeTimeStart, freeTimeEnd, hour_rate);
        assertThat(testee.getTotalHours()).isEqualTo(expected);
    }

    private static Stream<Arguments> getTotalHoursParamProvider() {
        return Stream.of(
                Arguments.of(createZonedDateTime(6, 0), createZonedDateTime(14, 0), 8),
                Arguments.of(createZonedDateTime(6, 59), createZonedDateTime(7, 1), 2),
                Arguments.of(createZonedDateTime(10, 0), createZonedDateTime(12, 0), 2),
                Arguments.of(createZonedDateTime(5, 0), createZonedDateTime(4, 0), 0),
                Arguments.of(createZonedDateTime(5, 0), createZonedDateTime(6, 50), 2),
                Arguments.of(createZonedDateTime(13, 0), createZonedDateTime(15, 0), 2)
        );
    }

    @ParameterizedTest
    @MethodSource("getPaidTimeInHoursParamProvider")
    void getPaidTimeInHours(ZonedDateTime entry_date, ZonedDateTime exit_date, int expected) {
        AttendanceRecord testee = new AttendanceRecord(entry_date, exit_date, freeTimeStart, freeTimeEnd, hour_rate);
        assertThat(testee.getPaidTimeInHours()).isEqualTo(expected);
    }

    private static Stream<Arguments> getPaidTimeInHoursParamProvider() {
        return Stream.of(
                Arguments.of(createZonedDateTime(6, 0), createZonedDateTime(14, 0), 3),
                Arguments.of(createZonedDateTime(6, 59), createZonedDateTime(7, 1), 1),
                Arguments.of(createZonedDateTime(10, 0), createZonedDateTime(12, 0), 0),
                Arguments.of(createZonedDateTime(5, 0), createZonedDateTime(4, 0), 0),
                Arguments.of(createZonedDateTime(5, 0), createZonedDateTime(6, 50), 2),
                Arguments.of(createZonedDateTime(13, 0), createZonedDateTime(15, 0), 2)
        );
    }

    @ParameterizedTest
    @MethodSource("getPaymentTotalParamProvider")
    void getPaymentTotal(ZonedDateTime entry_date, ZonedDateTime exit_date, BigDecimal expected) {
        AttendanceRecord testee = new AttendanceRecord(entry_date, exit_date, freeTimeStart, freeTimeEnd, hour_rate);
        assertThat(testee.getPaymentTotal()).isEqualTo(expected);
    }

    private static Stream<Arguments> getPaymentTotalParamProvider() {
        return Stream.of(
                Arguments.of(createZonedDateTime(6, 0), createZonedDateTime(14, 0), createPaymentValue("30")),
                Arguments.of(createZonedDateTime(6, 59), createZonedDateTime(7, 1), createPaymentValue("10")),
                Arguments.of(createZonedDateTime(10, 0), createZonedDateTime(12, 0), createPaymentValue("0")),
                Arguments.of(createZonedDateTime(5, 0), createZonedDateTime(4, 0), createPaymentValue("0")),
                Arguments.of(createZonedDateTime(5, 0), createZonedDateTime(6, 50), createPaymentValue("20")),
                Arguments.of(createZonedDateTime(13, 0), createZonedDateTime(15, 0), createPaymentValue("20"))
        );
    }
}