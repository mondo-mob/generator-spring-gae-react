package threewks.framework.usermanagement.dto;

import threewks.framework.usermanagement.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UpdateUserRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Set<Role> roles = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public UpdateUserRequest setFirstName(String firstName) {
        this.firstName= firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UpdateUserRequest setLastName(String lastName) {
        this.lastName= lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UpdateUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UpdateUserRequest grantRoles(Role... roles) {
        return grantRoles(Arrays.asList(roles));
    }

    public UpdateUserRequest grantRoles(Collection<Role> roles) {
        getRoles().addAll(roles);
        return this;
    }

}
