package threewks.testinfra.rules;

import org.junit.rules.ExternalResource;

import java.util.TimeZone;

/**
 * Sets the system timezone to UTC so that we can ensure tests do not depend on the current timezone of their
 * local environment for any business logic. This helps us trap unexpected behaviour when we deploy to environments
 * with a different timezone (namely, Google environments deliberately setup as UTC to avoid daylight savings shifts).
 */
public class TimeZoneUTC extends ExternalResource {

    private TimeZone origDefault = TimeZone.getDefault();

    @Override
    protected void before() throws Throwable {
        super.before();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    protected void after() {
        super.after();
        TimeZone.setDefault(origDefault);
    }
}
