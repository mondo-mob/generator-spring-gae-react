package threewks.framework.usermanagement.dto;

import threewks.framework.BaseDto;

public class UserSummary extends BaseDto {
    private String id;
    private String email;
    private String firstName;
    private String lastName;

    // Derived from names and are calculated at model
    private String name;

    public String getId() {
        return id;
    }

    public UserSummary setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserSummary setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserSummary setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserSummary setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserSummary setName(String name) {
        this.name = name;
        return this;
    }
}
