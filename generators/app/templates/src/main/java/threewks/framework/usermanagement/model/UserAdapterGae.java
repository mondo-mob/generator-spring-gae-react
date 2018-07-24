package threewks.framework.usermanagement.model;

import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import org.springframework.contrib.gae.security.UserAdapter;
import org.springframework.contrib.gae.util.Nulls;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import threewks.framework.AuthenticationException;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.framework.usermanagement.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserAdapterGae implements UserAdapter<User> {
    private static final Supplier<AuthenticationException> UNAUTHENTICATED_EXCEPTION = () -> new AuthenticationException("Authenticated user required");
    private final boolean byEmail;
    private final UserService userService;

    private UserAdapterGae(UserService userService, boolean byEmail) {
        this.userService = userService;
        this.byEmail = byEmail;
    }

    public static UserAdapterGae byEmail(UserService userService) {
        return new UserAdapterGae(userService, true);
    }

    public static UserAdapterGae byUsername(UserService userService) {
        return new UserAdapterGae(userService, false);
    }

    @Override
    public User newFromUserDetails(UserDetails userDetails) {
        User user = byEmail ?
            User.byEmail(userDetails.getUsername(), userDetails.getPassword()) :
            User.byUsername(userDetails.getUsername(), userDetails.getPassword());

        user.setRoles(transformToRoles(userDetails.getAuthorities()));
        user.setEnabled(userDetails.isEnabled());
        return user;
    }

    @Override
    public void mergeUserDetails(User user, UserDetails userDetails) {
        Set<Role> roles = transformToRoles(userDetails.getAuthorities());
        user.setRoles(roles);
        user.setPassword(userDetails.getPassword());
        user.setEnabled(userDetails.isEnabled());
    }

    @Override
    public void setPassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles();
    }

    @Override
    public UserDetails toUserDetails(User user) {
        return AuthUser.withId(user.getId())
            .username(byEmail ? user.getEmail() : user.getId())
            .password(user.getPassword())
            .authorities(new ArrayList<>(user.getRoles()))
            .disabled(!user.isEnabled())
            .build();
    }

    @Override
    public Optional<Key<User>> getUserKey(String username, Class<User> userClass) {
        return userService.get(username)
            .map(Key::create);
    }

    @SuppressWarnings("ConstantConditions")
    public User getCurrentUserRequired() {
        return getCurrentUser()
            .orElseThrow(UNAUTHENTICATED_EXCEPTION);
    }

    public static String currentUserIdRequired() {
        return currentUserId()
            .orElseThrow(UNAUTHENTICATED_EXCEPTION);
    }

    public Optional<User> getCurrentUser() {
        return currentUserId()
            .flatMap(userService::findById);
    }

    public static Optional<Ref<User>> currentUserRef() {
        return currentUserKey()
            .map(Ref::create);
    }

    public static Optional<Key<User>> currentUserKey() {
        return currentUserId()
            .map(id -> Key.create(User.class, id));
    }

    public static Optional<String> currentUserId() {
        return currentAuthUser()
            .map(AuthUser::getId);
    }

    public static void verifyCurrentUserOrAdmin(String userId) {
        currentAuthUser()
            .filter(usr -> containsAny(usr, Role.ADMIN) || usr.getId().equals(userId))
            .orElseThrow(() -> new AuthenticationException("Authenticated user does not have access to user " + userId));
    }

    private static Optional<AuthUser> currentAuthUser() {
        Object principal = Nulls.ifNotNull(SecurityContextHolder.getContext().getAuthentication(), Authentication::getPrincipal);
        if (principal instanceof AuthUser) {
            return Optional.of(((AuthUser) principal));
        }
        return Optional.empty();
    }

    private static boolean containsAny(AuthUser usr, Role... roles) {
        Set<Role> required = Sets.newHashSet(roles);
        return transformToRoles(usr.getAuthorities()).stream()
            .anyMatch(required::contains);
    }

    private static Set<Role> transformToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
            .map(Role::valueOf)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
