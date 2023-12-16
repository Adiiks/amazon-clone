package pl.adrian.amazon.data;

import pl.adrian.amazon.entity.Category;

public class CategoryDataBuilder {

    public static Category buildCategory() {
        return Category.builder()
                .id(1)
                .name("Video Games")
                .build();
    }
}
