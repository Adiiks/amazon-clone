package pl.adrian.amazon.converter;

import org.springframework.stereotype.Component;
import pl.adrian.amazon.dto.CategoryResponse;
import pl.adrian.amazon.entity.Category;

@Component
public class CategoryConverter {

    public CategoryResponse categoryToCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
