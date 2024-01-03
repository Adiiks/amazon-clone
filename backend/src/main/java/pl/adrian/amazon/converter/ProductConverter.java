package pl.adrian.amazon.converter;

import org.springframework.stereotype.Component;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.entity.Product;

@Component
public class ProductConverter {

    public Product productRequestToProduct(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .quantity(request.quantity())
                .build();
    }
}
