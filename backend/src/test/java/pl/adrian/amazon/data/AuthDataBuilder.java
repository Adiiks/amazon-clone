package pl.adrian.amazon.data;

import pl.adrian.amazon.dto.RegistrationRequest;

public class AuthDataBuilder {

    public static RegistrationRequest buildRegistrationRequest() {
        return new RegistrationRequest(
                "Jan Kowalski",
                "jan@wp.pl",
                "password"
        );
    }
}
