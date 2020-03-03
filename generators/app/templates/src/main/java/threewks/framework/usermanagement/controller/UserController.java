package threewks.framework.usermanagement.controller;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import threewks.controller.dto.SimpleRequest;
import threewks.framework.usermanagement.dto.RedeemInvitationRequest;
import threewks.framework.usermanagement.dto.UpdateUserRequest;
import threewks.framework.usermanagement.dto.UserDto;
import threewks.framework.usermanagement.dto.transformer.ToUserDto;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.framework.usermanagement.service.InviteUserRequest;
import threewks.framework.usermanagement.service.UserInviteService;
import threewks.framework.usermanagement.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static threewks.util.RestUtils.response;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserAdapterGae userAdapter;
    private final UserService userService;
    private final UserInviteService userInviteService;

    public UserController(UserAdapterGae userAdapter, UserService userService, UserInviteService userInviteService) {
        this.userAdapter = userAdapter;
        this.userService = userService;
        this.userInviteService = userInviteService;
    }

    @GetMapping("/me")
    public UserDto user(HttpServletResponse response) {
        Optional<User> currentUser = userAdapter.getCurrentUser();

        if (currentUser.isPresent()) {
            LOG.debug("Found existing authenticated user {}", currentUser.get().getEmail());
            return response(ToUserDto.INSTANCE.transform(currentUser));
        }

        LOG.debug("User not authenticated");
        response.setStatus(HttpStatus.SC_NO_CONTENT);
        return null;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto user(@PathVariable("userId") String userId) {
        return response(ToUserDto.INSTANCE.transform(userService.findById(userId)));
    }

    @PostMapping("/invite")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto inviteUser(@RequestBody @Valid InviteUserRequest inviteUserRequest) {
        return transform(userInviteService.invite(inviteUserRequest.getEmail(), new HashSet<>(inviteUserRequest.getRoles())));
    }

    @PostMapping("/invite/{inviteCode}")
    public UserDto redeemInvitation(@PathVariable("inviteCode") String inviteCode, @RequestBody RedeemInvitationRequest request) {
        return transform(userInviteService.redeem(inviteCode, request.getFirstName(), request.getLastName(), request.getPassword()));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto saveUser(@PathVariable("userId") String userId, @RequestBody @Valid UpdateUserRequest request) {
        return transform(userService.update(userId, request));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> list() {
        return transform(userService.list());
    }

    @PostMapping("/{userId}/change-password")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto changePassword(@PathVariable("userId") String userId, @RequestBody SimpleRequest<String> request) {
        return transform(userService.changePassword(userId, request.getValue()));
    }

    private UserDto transform(User user) {
        return ToUserDto.INSTANCE.transform(user);
    }

    private List<UserDto> transform(List<User> users) {
        return ToUserDto.INSTANCE.transform(users);
    }

}
