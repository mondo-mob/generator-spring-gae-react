package threewks.testinfra;

import org.springframework.test.util.ReflectionTestUtils;
import threewks.framework.BaseEntityCore;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.framework.usermanagement.model.User;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

public class TestData {

    public static User user(Role...roles) {
        return user("admin@3wks.com.au", roles);
    }

    public static User user(String email) {
        return user(email, Role.ADMIN, Role.SUPER);
    }

    public static User user(String email, Role...roles) {
        return User.byEmail(email, "myPass")
            .setFirstName("aFirstName")
            .setLastName("aLastName")
            .setRoles(Arrays.asList(roles));
    }

    public static <T extends BaseEntityCore> T setCreatedUpdated(T entity) {
        ReflectionTestUtils.setField(entity, "created", OffsetDateTime.now().minusDays(1));
        ReflectionTestUtils.setField(entity, "updated", OffsetDateTime.now());
        return entity;
    }

    public static AuthUser authUser(Role... roles) {
        return new AuthUser("id", "bob@email.org", "password", Arrays.asList(roles));
    }

    private static String uuid() {
        return UUID.randomUUID().toString();
    }

}
