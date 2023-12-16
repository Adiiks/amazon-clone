package pl.adrian.amazon.service;

import pl.adrian.amazon.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getCategories();
}
