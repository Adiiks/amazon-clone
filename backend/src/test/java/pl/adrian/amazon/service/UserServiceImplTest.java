package pl.adrian.amazon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.converter.UserConverter;
import pl.adrian.amazon.data.UserDataBuilder;
import pl.adrian.amazon.dto.UserResponse;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    UserConverter userConverter = new UserConverter();

    User userDb;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userConverter);

        userDb = UserDataBuilder.buildUser();
    }

    @DisplayName("Get user by email - not found")
    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUserByEmail(userDb.getEmail()));
    }

    @DisplayName("Get user by email - success")
    @Test
    void getUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userDb));

        UserResponse user = userService.getUserByEmail(userDb.getEmail());

        assertEquals(userDb.getId(), user.id());
        assertEquals(userDb.getFullName(), user.fullName());
    }
}