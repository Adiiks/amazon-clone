package pl.adrian.amazon.dto;

public record LoginResponse(
        String token,
        long expiresIn
) {
}
