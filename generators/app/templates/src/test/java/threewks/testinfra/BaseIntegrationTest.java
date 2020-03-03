package threewks.testinfra;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.contrib.gae.objectify.ObjectifyProxy;
import org.springframework.contrib.gae.search.SearchService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import threewks.framework.usermanagement.repository.UserRepository;
import threewks.testinfra.rules.LocalServicesRule;
import threewks.util.TestDateTimeUtils;

import static com.googlecode.objectify.ObjectifyService.ofy;

@ActiveProfiles("junit")
@ContextConfiguration(classes = TestApplicationContext.class)
@SpringBootTest
public abstract class BaseIntegrationTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    @Rule
    public LocalServicesRule localServicesRule = new LocalServicesRule();

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ObjectifyProxy objectifyProxy;
    @Autowired
    protected SearchService searchService;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @After
    public void baseAfter() {
        // Catch all in case tests don't clean up
        TestDateTimeUtils.setClockSystem();
    }

    @SuppressWarnings("unchecked")
    protected <E> E save(E entity) {
        ofy().save().entities(entity).now();
        return entity;
    }

}
