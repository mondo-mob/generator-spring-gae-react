package threewks.framework.usermanagement.dto.transformer;

import org.junit.Before;
import org.junit.Test;
import threewks.framework.usermanagement.dto.UserSummary;
import threewks.framework.usermanagement.model.User;
import threewks.testinfra.TestData;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ToUserSummaryTest {

    private User user;

    @Before
    public void before() {
        user = TestData.setCreatedUpdated(TestData.user());
    }

    @Test
    public void apply_willTransform_excludingPassword() {
        UserSummary dto = ToUserSummary.INSTANCE.apply(user);

        assertUserMatches(user, dto);
    }

    @Test
    public void fromInstance_willPopulateEmptySubclassInstance() {
        TestUserSummary dto = ToUserSummary.INSTANCE.fromInstance(user, new TestUserSummary());

        assertUserMatches(user, dto);
    }

    private static void assertUserMatches(User user, UserSummary dto) {
        assertThat(dto.getId(), is(user.getId()));
        assertThat(dto.getEmail(), is(user.getEmail()));
        assertThat(dto.getFirstName(), is(user.getFirstName()));
        assertThat(dto.getLastName(), is(user.getLastName()));
        assertThat(dto.getName(), is(user.getFullName()));
        assertThat(dto.getCreated(), is(user.getCreated()));
        assertThat(dto.getUpdated(), is(user.getUpdated()));
    }

    private class TestUserSummary extends UserSummary {

    }

}
