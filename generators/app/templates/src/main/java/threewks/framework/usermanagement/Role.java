package threewks.framework.usermanagement;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import threewks.framework.ref.ReferenceData;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role implements GrantedAuthority, ReferenceData {
    SUPER,
    ADMIN,
    USER;

    private final String description;

    Role() {
        this.description = StringUtils.capitalize(name().toLowerCase());
    }

    Role(String description) {
        this.description = description;
    }

    public static Role valueOf(GrantedAuthority authority) {
        String role = authority.getAuthority().replace("ROLE_", "");
        return valueOf(role);
    }

    @Override
    public String getAuthority() {
        return String.format("ROLE_%s", this.name());
    }

    public static Set<Role> parseSet(Set<String> stringRoles) {
        return stringRoles.stream().map(s -> Role.valueOf(s.trim().toUpperCase())).collect(Collectors.toSet());
    }

    @Override
    public String getDescription() {
        return description;
    }
}
