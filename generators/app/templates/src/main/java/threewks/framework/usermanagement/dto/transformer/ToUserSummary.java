package threewks.framework.usermanagement.dto.transformer;

import threewks.framework.BaseDto;
import threewks.framework.Transformer;
import threewks.framework.usermanagement.dto.UserSummary;
import threewks.framework.usermanagement.model.User;

public class ToUserSummary implements Transformer<User, UserSummary> {

    public static final ToUserSummary INSTANCE = new ToUserSummary();

    @Override
    public UserSummary apply(User user) {
        return fromInstance(user, new UserSummary());
    }

    public <T extends UserSummary> T fromInstance(User user, T existing) {
        T dto = BaseDto.fromEntity(existing, user);
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setName(user.getFullName());
        return dto;
    }
}
