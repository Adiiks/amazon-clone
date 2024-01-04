package pl.adrian.amazon.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank(message = "Product description is required")
        String description,

        @NotNull(message = "Product price is required")
        @Positive(message = "Product price must be a positive number")
        BigDecimal price,

        @NotNull(message = "Product quantity is required")
        @Min(value = 1, message = "Product quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Category ID is required")
        Integer categoryId
) {
}
