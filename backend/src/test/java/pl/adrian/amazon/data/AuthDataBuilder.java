package pl.adrian.amazon.data;

import pl.adrian.amazon.dto.LoginRequest;
import pl.adrian.amazon.dto.RegistrationRequest;

public class AuthDataBuilder {

    public static RegistrationRequest buildRegistrationRequest() {
        return new RegistrationRequest(
                "Jan Kowalski",
                "jan@wp.pl",
                "password"
        );
    }

    public static LoginRequest buildLoginRequest() {
        return new LoginRequest(
                "jan@gmail.com",
                "password"
        );
    }
}
