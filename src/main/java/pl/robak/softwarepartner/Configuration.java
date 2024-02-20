package pl.robak.softwarepartner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

@org.springframework.context.annotation.Configuration
@PropertySource("classpath:application.properties")
public class Configuration {

    @Value("${freetime.start}")
    private String freeTimeStart;

    @Value("${freetime.end}")
    private String freeTimeEnd;

    public OffsetTime getFreeTimeStart() {
        return LocalTime.parse(freeTimeStart).atOffset(ZoneOffset.UTC);
    }

    public OffsetTime getFreeTimeEnd() {
        return LocalTime.parse(freeTimeEnd).atOffset(ZoneOffset.UTC);
    }
}
