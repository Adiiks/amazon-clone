package pl.adrian.amazon.service;

import pl.adrian.amazon.dto.UserResponse;

public interface UserService {

    UserResponse getUserByEmail(String userEmail);
}
