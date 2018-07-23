package threewks.testinfra.rules;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.dto.AuthUser;
import threewks.framework.usermanagement.model.User;
import threewks.framework.usermanagement.model.UserAdapterGae;
import threewks.testinfra.TestData;

import static org.mockito.Mockito.when;

public class SecurityContextRule extends SecurityContextReset {
    private static final UserAdapterGae USER_ADAPTER = UserAdapterGae.byEmail(null);
    private final AuthUser authUser;
    private final User user;

    public SecurityContextRule() {
        this(TestData.user());
    }

    public SecurityContextRule(User user) {
        this.user = user;
        this.authUser = (AuthUser) USER_ADAPTER.toUserDetails(user);
    }

    @Override
    protected void before() {
        initialiseSecurityContext(authUser);
    }


    public AuthUser getAuthUser() {
        return authUser;
    }

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return user.getId();
    }

    public static void initialiseSecurityContextWithRoles(Role... roles) {
        AuthUser authUser = (AuthUser) USER_ADAPTER.toUserDetails(TestData.user(roles));
        initialiseSecurityContext(authUser);
    }

    private static void initialiseSecurityContext(AuthUser authUser) {
        SecurityContextHolder.clearContext();
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(authUser);
    }

}
