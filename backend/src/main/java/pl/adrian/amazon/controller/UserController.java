package pl.adrian.amazon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.amazon.dto.UserResponse;
import pl.adrian.amazon.security.AuthenticationFacade;
import pl.adrian.amazon.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public UserResponse getAuthenticatedUser() {
        String userEmail = authenticationFacade.getEmail();

        return userService.getUserByEmail(userEmail);
    }
}
