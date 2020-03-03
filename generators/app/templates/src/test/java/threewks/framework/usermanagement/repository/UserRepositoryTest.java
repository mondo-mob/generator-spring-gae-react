package threewks.framework.usermanagement.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.model.User;
import threewks.testinfra.BaseIntegrationTest;
import threewks.testinfra.TestData;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class UserRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findEnabledByRole_willMatchingEnabledUsers() {
        testHelper.saveUser("user-1@email.org", Role.ADMIN);
        testHelper.saveUser("user-2@email.org", Role.SUPER);
        testHelper.saveUser("user-3@email.org", Role.USER, Role.ADMIN);
        testHelper.saveUser(TestData.user(Role.ADMIN).setEnabled(false));

        List<User> users = userRepository.findEnabledByRole(Role.ADMIN);

        assertThat(users.stream().map(User::getEmail).collect(toList()), containsInAnyOrder("user-1@email.org", "user-3@email.org"));
    }
}
