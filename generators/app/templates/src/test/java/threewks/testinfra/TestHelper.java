package threewks.testinfra;

import org.springframework.stereotype.Component;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.repository.UserRepository;

/**
 * Convenience helper for saving and retrieving test objects to/from db. This saves injecting multiple
 * repositories and/or services into tests for setup.
 */
@Component
public class TestHelper {
    private final UserRepository userRepository;

    public TestHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(Role... roles) {
        return saveUser(TestData.user(roles));
    }

    public User saveUser(String email, Role... roles) {
        return saveUser(TestData.user(email, roles));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
