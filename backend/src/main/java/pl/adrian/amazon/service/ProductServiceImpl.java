package pl.adrian.amazon.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.converter.ProductConverter;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.dto.ProductResponse;
import pl.adrian.amazon.entity.Category;
import pl.adrian.amazon.entity.Product;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.repository.CategoryRepository;
import pl.adrian.amazon.repository.ProductRepository;
import pl.adrian.amazon.repository.UserRepository;
import pl.adrian.amazon.utils.ImageValidator;

import java.util.List;

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

    @Override
    public ProductResponse getProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(productConverter::productToProductResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product not exists"));
    }

    @Override
    public Page<ProductResponse> getProductsByCategoryId(Integer categoryId, Pageable pageable, String search) {
        Page<Product> productPage = (StringUtils.hasText(search)) ?
                productRepository.findByCategory_IdAndNameContainsIgnoreCase(categoryId, search, pageable) :
                productRepository.findByCategory_IdOrderByIdDesc(categoryId, pageable);

        List<ProductResponse> products = productPage.getContent()
                .stream()
                .map(productConverter::productToProductResponse)
                .toList();

        return new PageImpl<>(products, productPage.getPageable(), productPage.getTotalElements());
    }

    @Override
    public Page<ProductResponse> getProducts(String search, Pageable pageable) {
        Page<Product> productPage = (StringUtils.hasText(search)) ?
                productRepository.findByNameContainsIgnoreCase(search, pageable) :
                productRepository.findAll(pageable);

        List<ProductResponse> productsResponse = productPage.getContent()
                .stream()
                .map(productConverter::productToProductResponse)
                .toList();

        return new PageImpl<>(productsResponse, productPage.getPageable(), productPage.getTotalElements());
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
