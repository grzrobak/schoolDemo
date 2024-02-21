package pl.robak.softwarepartner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.robak.softwarepartner.model.summary.AttendanceRecord;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TestUtils {

    public static BigDecimal createPaymentValue(String value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
    }

    public static ZonedDateTime createZonedDateTime(int hour, int minute) {
        return ZonedDateTime.of(2024, 1, 1, hour, minute, 0, 0, ZoneOffset.UTC);
    }

    public static AttendanceRecord newAttendance(ZonedDateTime entry_date, ZonedDateTime exit_date) {
        OffsetTime freeTimeStart = OffsetTime.of(7, 0, 0, 0, ZoneOffset.UTC);
        OffsetTime freeTimeEnd = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
        BigDecimal hour_rate = BigDecimal.TEN;

        return new AttendanceRecord(entry_date, exit_date, freeTimeStart, freeTimeEnd, hour_rate);
    }

    public static JsonNode toJsonTree(String response) throws JsonProcessingException {
        return new ObjectMapper().readTree(response);
    }

    public static String readFromFile(String filePath) throws Exception {
        InputStream inputStream = TestUtils.class.getResourceAsStream(filePath);
        return new String(inputStream.readAllBytes());
    }

}
