package pl.adrian.amazon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.adrian.amazon.data.AuthDataBuilder;
import pl.adrian.amazon.dto.LoginRequest;
import pl.adrian.amazon.dto.RegistrationRequest;
import pl.adrian.amazon.service.AuthService;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    AuthController authController;

    @Mock
    AuthService authService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new ValidationErrorControllerAdvice())
                .build();
    }

    @DisplayName("Register new user - invalid body")
    @Test
    void register_InvalidBody() throws Exception {
        RegistrationRequest request = new RegistrationRequest("", "invalid-format", "aa");

        mockMvc.perform(post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(authService, times(0)).register(any());
    }

    @DisplayName("Register new user - success")
    @Test
    void register_Success() throws Exception {
        RegistrationRequest request = AuthDataBuilder.buildRegistrationRequest();

        mockMvc.perform(post("/api/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(authService).register(any());
    }

    @DisplayName("Login - invalid body")
    @Test
    void login_InvalidBody() throws Exception {
        LoginRequest request = new LoginRequest("asdsadas", "");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(authService, times(0)).login(any());
    }

    @DisplayName("Login - success")
    @Test
    void login() throws Exception {
        LoginRequest request = AuthDataBuilder.buildLoginRequest();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(authService).login(any());
    }
}