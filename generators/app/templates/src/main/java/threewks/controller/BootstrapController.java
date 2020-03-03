package threewks.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.service.UserService;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RestController
public class BootstrapController {

    private UserService userService;
    private final String adminUsername;

    public BootstrapController(UserService userService, @Value("${app.bootstrap.adminUsername}") String adminUsername) {
        this.userService = userService;
        this.adminUsername = adminUsername;
    }

    @PostMapping("/system/bootstrap/super-user")
    public void bootstrapSuperUser(
        @RequestParam("email") Optional<String> email,
        @RequestParam("password") String password) {

        UpdateUserRequest request = new UpdateUserRequest()
            .setEmail(email.orElse(adminUsername))
            .setFirstName("Super")
            .setLastName("User")
            .grantRoles(Role.SUPER, Role.ADMIN);

        userService.create(request, password);
    }
}
