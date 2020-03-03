package threewks.framework.usermanagement.service.bootstrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.repository.UserRepository;
import threewks.framework.usermanagement.service.UserService;
import threewks.framework.service.bootstrap.Bootstrapper;

@Profile("local")
@Component
public class UserBootstrapper implements Bootstrapper {
    private static final Logger LOG = LoggerFactory.getLogger(UserBootstrapper.class);
    private final UserService userService;
    private final UserRepository userRepository;
    private final String adminUsername;

    public UserBootstrapper(UserService userService, UserRepository userRepository, @Value("${app.bootstrap.adminUsername}") String adminUsername) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.adminUsername = adminUsername;
    }

    @Override
    public void bootstrap() {
        if (userRepository.findAll(1).isEmpty()) {
            LOG.info("Bootstrapping users...");
            UpdateUserRequest request = new UpdateUserRequest()
                .setEmail(adminUsername)
                .setFirstName("Admin")
                .setLastName("User")
                .grantRoles(Role.ADMIN, Role.SUPER);
            userService.create(request, "password");
        } else {
            LOG.info("Skipping user bootstrap. Users exist.");
        }

    }

}
