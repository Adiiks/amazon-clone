package pl.adrian.amazon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.converter.UserConverter;
import pl.adrian.amazon.dto.UserResponse;
import pl.adrian.amazon.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserResponse getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .map(userConverter::userToUserResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
