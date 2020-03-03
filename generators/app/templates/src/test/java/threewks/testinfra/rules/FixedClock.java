package threewks.testinfra.rules;


import org.junit.rules.ExternalResource;
import threewks.util.DateTimeUtils;
import threewks.util.TestDateTimeUtils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Sets a fixed clock so that any calls to {@link DateTimeUtils#now()} will return a fixed value.
 * <p>
 * This allows you to test timestamps without having to know the system time value when the property was set, by always asserting against {@link DateTimeUtils#now()}.
 */
public class FixedClock extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        super.before();
        TestDateTimeUtils.setClockTime(OffsetDateTime.now());
    }

    @Override
    protected void after() {
        super.after();
        TestDateTimeUtils.setClockSystem();
    }

    public OffsetDateTime incrementOneSecond() {
        return incrementSeconds(1);
    }

    public OffsetDateTime incrementSeconds(int seconds) {
        return setClockTime(DateTimeUtils.now().plusSeconds(seconds));
    }

    public OffsetDateTime incrementOneMinute() {
        return incrementMinutes(1);
    }

    public OffsetDateTime incrementMinutes(int minutes) {
        return setClockTime(DateTimeUtils.now().plusMinutes(minutes));
    }

    public OffsetDateTime incrementDays(int days) {
        return setClockTime(DateTimeUtils.now().plusDays(days));
    }

    public OffsetDateTime decrementDays(int days) {
        return setClockTime(DateTimeUtils.now().minusDays(days));
    }

    public OffsetDateTime incrementWeeks(int weeks) {
        return setClockTime(DateTimeUtils.now().plusWeeks(weeks));
    }

    public OffsetDateTime decrementWeeks(int weeks) {
        return setClockTime(DateTimeUtils.now().minusWeeks(weeks));
    }

    public OffsetDateTime setDayOfMonth(int day) {
        return setClockTime(DateTimeUtils.now().withDayOfMonth(day));
    }

    public OffsetDateTime setDate(String dateString) {
        return setClockTime(
            LocalDate.parse(dateString)
                .atStartOfDay()
                .atOffset(ZoneOffset.UTC)
        );
    }

    private OffsetDateTime setClockTime(OffsetDateTime newNowTime) {
        TestDateTimeUtils.setClockTime(newNowTime);
        return newNowTime;
    }

}

