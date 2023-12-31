package pl.adrian.amazon.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.adrian.amazon.data.UserDataBuilder;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.security.AuthenticationFacade;
import pl.adrian.amazon.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Mock
    AuthenticationFacade authenticationFacade;

    @Mock
    UserService userService;

    User userDb;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDb = UserDataBuilder.buildUser();

        when(authenticationFacade.getEmail()).thenReturn(userDb.getEmail());
    }

    @DisplayName("Get authenticated user - success")
    @Test
    void getAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/api/users/user"))
                .andExpect(status().isOk());

        verify(userService).getUserByEmail(anyString());
    }
}