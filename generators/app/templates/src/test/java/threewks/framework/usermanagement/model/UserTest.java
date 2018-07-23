package threewks.framework.usermanagement.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void byEmail_setsFieldsAndLowerCasesEmail() {
        User user = User.byEmail("EMaiL@gmaIL.COm", "password");
        assertThat(user.getId().length(), is(36));
        assertThat(user.getEmail(), is("email@gmail.com"));
        assertThat(user.getPassword(), is("password"));
        assertThat(user.isEnabled(), is(true));
    }

    @Test
    public void invitedByEmail_setsFieldsAndLowerCasesEmail() {
        User user = User.invitedByEmail("EMaiL@gmaIL.COm");
        assertThat(user.getId().length(), is(36));
        assertThat(user.getEmail(), is("email@gmail.com"));
        assertThat(user.isEnabled(), is(false));
    }

    @Test
    public void setEmail_lowerCasesEmail() {
        User user = User.byEmail("email", "password");
        user.setEmail("EMaiL@gmaIL.COm");
        assertThat(user.getEmail(), is("email@gmail.com"));
    }

}
