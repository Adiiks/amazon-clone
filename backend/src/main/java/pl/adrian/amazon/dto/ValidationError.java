package pl.adrian.amazon.dto;

public record ValidationError(
        String fieldName,
        String errorMessage
) {
}
