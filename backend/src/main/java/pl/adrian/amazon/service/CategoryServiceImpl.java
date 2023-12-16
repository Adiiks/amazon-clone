package pl.adrian.amazon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.adrian.amazon.converter.CategoryConverter;
import pl.adrian.amazon.dto.CategoryResponse;
import pl.adrian.amazon.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryConverter::categoryToCategoryResponse)
                .toList();
    }
}
