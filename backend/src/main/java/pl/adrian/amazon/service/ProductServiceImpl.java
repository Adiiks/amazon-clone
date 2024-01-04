package pl.adrian.amazon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.converter.ProductConverter;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.entity.Category;
import pl.adrian.amazon.entity.Product;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.repository.CategoryRepository;
import pl.adrian.amazon.repository.ProductRepository;
import pl.adrian.amazon.repository.UserRepository;
import pl.adrian.amazon.utils.ImageValidator;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageValidator imageValidator;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Integer createProduct(MultipartFile image, ProductRequest product, String userEmail) {
        if (!imageValidator.isImageTypeAllowed(image)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File extension is not allowed. Only png and jpg");
        }

        User loggedUser = findUser(userEmail);
        Category category = findCategory(product.categoryId());

        String imageUrl = imageService.uploadImage(image);

        Product productToSave = productConverter.productRequestToProduct(product);
        productToSave.setImageUrl(imageUrl);
        productToSave.setCategory(category);
        productToSave.setSoldByUser(loggedUser);

        return productRepository.save(productToSave).getId();
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found"));
    }

    private Category findCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not exists");
        }

        return categoryRepository.getReferenceById(id);
    }
}
