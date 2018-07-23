package threewks.framework.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.springframework.contrib.gae.security.GaeUser;
import threewks.framework.BaseEntityCore;
import threewks.framework.usermanagement.Role;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Cache
@Entity
public class User extends BaseEntityCore implements GaeUser, Serializable {
    @Id
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new LinkedHashSet<>();
    private boolean enabled = true;

    private User() {
    }

    public static User byEmail(String email, String password) {
        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.password = password;
        user.setEmail(email);
        return user;
    }

    public static User invitedByEmail(String email) {
        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.enabled = false;
        user.setEmail(email);
        return user;
    }

    public static User byUsername(String username, String password) {
        User user = new User();
        user.id = username;
        user.password = password;
        return user;
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email.toLowerCase();
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Collection<Role> roles) {
        this.roles = new LinkedHashSet<>(roles);
        return this;
    }

    public User grantRoles(Role... role) {
        return grantRoles(Arrays.asList(role));
    }

    public User grantRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public User revokeRoles(Role... role) {
        return revokeRoles(Arrays.asList(role));
    }

    public User revokeRoles(Collection<Role> roles) {
        this.roles.removeAll(roles);
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
