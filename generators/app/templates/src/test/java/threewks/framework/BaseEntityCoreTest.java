package threewks.framework;

import org.junit.Before;
import org.junit.Test;
import threewks.testinfra.BaseTest;
import threewks.testinfra.TestData;

import java.time.Duration;
import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BaseEntityCoreTest extends BaseTest {

    private TestBaseEntityCore testEntity;

    @Before
    public void before() {
        testEntity = new TestBaseEntityCore();
    }

    @Test
    public void getDurationFromCreatedToUpdated() {
        OffsetDateTime now = OffsetDateTime.now();
        Duration expectedDuration = Duration.ofHours(1);
        TestData.setCreatedUpdated(testEntity, now.minus(expectedDuration), now);

        Duration result = testEntity.getDurationFromCreatedToUpdated();

        assertThat(result, equalTo(expectedDuration));
    }

    @Test
    public void getDurationFromCreatedToUpdated_willError_whenCreatedNull() {
        TestData.setCreatedUpdated(testEntity, null, OffsetDateTime.now());

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Duration can not be calculated on entity not yet persisted");
        testEntity.getDurationFromCreatedToUpdated();
    }

    @Test
    public void getDurationFromCreatedToUpdated_willError_whenUpdatedNull() {
        TestData.setCreatedUpdated(testEntity, OffsetDateTime.now(), null);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Duration can not be calculated on entity not yet persisted");
        testEntity.getDurationFromCreatedToUpdated();
    }


    private static class TestBaseEntityCore extends BaseEntityCore {

    }

}
