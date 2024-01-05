package pl.adrian.amazon.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.dto.ProductResponse;
import pl.adrian.amazon.entity.Product;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final UserConverter userConverter;

    public Product productRequestToProduct(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .build();
    }

    public ProductResponse productToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getImageUrl(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                userConverter.userToUserResponse(product.getSoldByUser())
        );
    }
}
