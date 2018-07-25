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
        OffsetDateTime now = OffsetDateTime.now();
        return setCreatedUpdated(entity, now.minusDays(1), now);
    }

    public static <T extends BaseEntityCore> T setCreatedUpdated(T entity, OffsetDateTime created, OffsetDateTime updated) {
        ReflectionTestUtils.setField(entity, "created", created);
        ReflectionTestUtils.setField(entity, "updated", updated);
        return entity;
    }

    public static <T extends BaseEntityCore> T setCreatedUpdatedAndBy(T entity) {
        setCreatedUpdated(entity);
        MockHelpers.setRef(entity, "createdBy", TestData.user("createdBy@email.com"));
        MockHelpers.setRef(entity, "updatedBy", TestData.user("updatedBy@email.com"));
        return entity;
    }

    public static AuthUser authUser(Role... roles) {
        return new AuthUser("id", "bob@email.org", "password", Arrays.asList(roles));
    }

    private static String uuid() {
        return UUID.randomUUID().toString();
    }

}
