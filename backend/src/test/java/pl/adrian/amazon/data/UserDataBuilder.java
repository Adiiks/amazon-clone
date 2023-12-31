package pl.adrian.amazon.data;

import pl.adrian.amazon.entity.User;

public class UserDataBuilder {

    public static User buildUser() {
        return User.builder()
                .id(1)
                .fullName("Jan Kowalski")
                .email("jan.kowalski@gmail.com")
                .build();
    }
}
