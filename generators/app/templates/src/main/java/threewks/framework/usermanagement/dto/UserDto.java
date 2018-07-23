package threewks.framework.usermanagement.dto;

import threewks.framework.BaseDto;
import threewks.framework.usermanagement.Role;

import java.util.LinkedHashSet;
import java.util.Set;

public class UserDto extends BaseDto {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new LinkedHashSet<>();
    private boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
