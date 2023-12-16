package pl.adrian.amazon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.adrian.amazon.converter.CategoryConverter;
import pl.adrian.amazon.data.CategoryDataBuilder;
import pl.adrian.amazon.dto.CategoryResponse;
import pl.adrian.amazon.entity.Category;
import pl.adrian.amazon.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    CategoryConverter categoryConverter = new CategoryConverter();

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(
                categoryRepository,
                categoryConverter
        );
    }

    @DisplayName("Get list of categories")
    @Test
    void getCategories() {
        Category categoryDb = CategoryDataBuilder.buildCategory();

        when(categoryRepository.findAll()).thenReturn(List.of(categoryDb));

        List<CategoryResponse> result = categoryService.getCategories();

        CategoryResponse category = result.get(0);
        assertEquals(categoryDb.getId(), category.id());
        assertEquals(categoryDb.getName(), category.name());
    }
}