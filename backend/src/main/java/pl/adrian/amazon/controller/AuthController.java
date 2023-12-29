package pl.adrian.amazon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.adrian.amazon.dto.LoginRequest;
import pl.adrian.amazon.dto.LoginResponse;
import pl.adrian.amazon.dto.RegistrationRequest;
import pl.adrian.amazon.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegistrationRequest request) {
        authService.register(request);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
