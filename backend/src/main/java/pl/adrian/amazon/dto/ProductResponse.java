package pl.adrian.amazon.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String imageUrl,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        UserResponse soldByUser
) {
}
