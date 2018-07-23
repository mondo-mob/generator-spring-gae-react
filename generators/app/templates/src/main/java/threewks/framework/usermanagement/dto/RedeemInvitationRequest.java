package threewks.framework.usermanagement.dto;

public class RedeemInvitationRequest extends threewks.util.ValueObject {
    private String firstName;
    private String lastName;
    private String password;

    public RedeemInvitationRequest() {
    }

    public RedeemInvitationRequest(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
