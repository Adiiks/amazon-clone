package pl.adrian.amazon.data;

import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.entity.Product;

import java.math.BigDecimal;

public class ProductDataBuilder {

    public static ProductRequest buildProductRequest() {
        return new ProductRequest(
                "Movie - Mission Impossible: Fallout",
                "Movie with Tom Cruise as a main actor",
                BigDecimal.valueOf(15.99),
                5,
                1
        );
    }

    public static Product buildProduct() {
        return Product.builder()
                .id(1)
                .imageUrl("http://cloudinary.com/images/1321312")
                .name("Movie - Mission Impossible: Fallout")
                .description("Movie with Tom Cruise as a main actor")
                .price(BigDecimal.valueOf(15.99))
                .quantity(5)
                .soldByUser(UserDataBuilder.buildUser())
                .category(CategoryDataBuilder.buildCategory())
                .build();
    }
}
