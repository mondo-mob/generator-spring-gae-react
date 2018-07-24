package threewks.framework.usermanagement.dto.transformer;

import threewks.framework.BaseDto;
import threewks.framework.Transformer;
import threewks.framework.usermanagement.dto.UserDto;
import threewks.framework.usermanagement.model.User;

public class ToUserDto implements Transformer<User, UserDto> {

    public static final ToUserDto INSTANCE = new ToUserDto();

    @Override
    public UserDto apply(User user) {
        return fromInstance(user, new UserDto());
    }

    public <T extends UserDto> T fromInstance(User user, T existing) {
        T dto = ToUserSummary.INSTANCE.fromInstance(user, BaseDto.fromEntity(existing, user));
        dto.setEnabled(user.isEnabled());
        dto.getRoles().addAll(user.getRoles());
        return dto;
    }
}
