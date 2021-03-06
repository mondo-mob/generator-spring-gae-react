package threewks.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public final class DateTimeUtils {
    private static Clock clock = Clock.systemDefaultZone();

    private DateTimeUtils() {
        //static
    }

    public static OffsetDateTime toStartOfDay(OffsetDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.DAYS);
    }

    /**
     * Allows static mocking of {@link Clock} for tests without having to pollute our code with clock injections
     * everywhere. The hackiness of static in this case is better than code pollution.
     */
    public static OffsetDateTime now() {
        return OffsetDateTime.now(DateTimeUtils.clock);
    }

    public static OffsetDateTime now(ZoneId zoneId) {
        return OffsetDateTime.now(clock.withZone(zoneId));
    }

    public static LocalDate nowLocalDate() {
        return LocalDate.now(clock);
    }

    public static LocalDate nowLocalDate(ZoneId zoneId) {
        return LocalDate.now(clock.withZone(zoneId));
    }

    static void setClockSystem() {
        DateTimeUtils.clock = Clock.systemDefaultZone();
    }

    static void setClock(Instant instant, ZoneId zoneId) {
        DateTimeUtils.clock = Clock.fixed(instant, zoneId);
    }

}
