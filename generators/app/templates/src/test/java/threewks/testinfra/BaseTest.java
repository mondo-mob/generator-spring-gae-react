package threewks.testinfra;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import threewks.testinfra.rules.LocalServicesRule;
import threewks.testinfra.rules.SecurityContextReset;
import threewks.testinfra.rules.TimeZoneUTC;

@RunWith(MockitoJUnitRunner.Silent.class)
public abstract class BaseTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public LocalServicesRule localServicesRule = new LocalServicesRule();

    @Rule
    public TimeZoneUTC timeZoneUTC = new TimeZoneUTC();

    @Rule
    public SecurityContextReset securityContextReset = new SecurityContextReset();

}
