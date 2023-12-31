package pl.adrian.amazon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.data.AuthDataBuilder;
import pl.adrian.amazon.dto.RegistrationRequest;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.repository.UserRepository;
import pl.adrian.amazon.security.JwtUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Captor
    ArgumentCaptor<User> userAc;

    @BeforeEach
    void setUp() {
        authService = new AuthService(
                userRepository,
                passwordEncoder,
                authenticationManager,
                jwtUtils
        );
    }

    @DisplayName("Register new user - email already in use")
    @Test
    void register_EmailAlreadyInUse() {
        RegistrationRequest request = AuthDataBuilder.buildRegistrationRequest();

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> authService.register(request));

        verify(userRepository, times(0)).save(any());
    }

    @DisplayName("Register new user - success")
    @Test
    void register_Success() {
        RegistrationRequest request = AuthDataBuilder.buildRegistrationRequest();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        authService.register(request);

        verify(userRepository).save(userAc.capture());

        User user = userAc.getValue();
        assertNull(user.getId());
        assertEquals(request.fullName(), user.getFullName());
        assertEquals(request.email(), user.getEmail());
        assertNotEquals(request.password(), user.getPassword());
    }
}