package threewks.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Utility just for test classes ... exposes methods to update system clock
 */
public class TestDateTimeUtils {

    public static void setClockTime(OffsetDateTime offsetDateTime) {
        setClockTime(offsetDateTime.toInstant(), offsetDateTime.getOffset());
    }

    public static void setClockTime(Instant instant, ZoneId zoneId) {
        DateTimeUtils.setClock(instant, zoneId);
    }

    public static void setClockSystem() {
        DateTimeUtils.setClockSystem();
    }

}
