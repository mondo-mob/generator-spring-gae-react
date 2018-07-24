package threewks.framework.usermanagement.dto;

import threewks.framework.usermanagement.Role;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class UserDto extends UserSummary {
    private Set<Role> roles = new LinkedHashSet<>();
    private boolean enabled;

    public Set<Role> getRoles() {
        return roles;
    }

    public UserDto addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
