package pl.adrian.amazon.converter;

import org.springframework.stereotype.Component;
import pl.adrian.amazon.dto.UserResponse;
import pl.adrian.amazon.entity.User;

@Component
public class UserConverter {

    public UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName()
        );
    }
}
