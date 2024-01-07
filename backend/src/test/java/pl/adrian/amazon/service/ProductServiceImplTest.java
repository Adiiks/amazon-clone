package pl.adrian.amazon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.converter.ProductConverter;
import pl.adrian.amazon.converter.UserConverter;
import pl.adrian.amazon.data.ProductDataBuilder;
import pl.adrian.amazon.data.UserDataBuilder;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.dto.ProductResponse;
import pl.adrian.amazon.entity.Product;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.repository.CategoryRepository;
import pl.adrian.amazon.repository.ProductRepository;
import pl.adrian.amazon.repository.UserRepository;
import pl.adrian.amazon.utils.ImageValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ImageService imageService;

    @Mock
    CategoryRepository categoryRepository;

    ImageValidator imageValidator = new ImageValidator();

    UserConverter userConverter = new UserConverter();

    ProductConverter productConverter = new ProductConverter(userConverter);

    @Captor
    ArgumentCaptor<Product> productAc;

    User loggedUser;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(
                productRepository,
                userRepository,
                imageService,
                imageValidator,
                productConverter,
                categoryRepository
        );

        loggedUser = UserDataBuilder.buildUser();
    }

    @DisplayName("Create new product - wrong image extension")
    @Test
    void createProductImageValidationFailed() {
        MockMultipartFile image = new MockMultipartFile("image", "image.pdf", "", "image content".getBytes());
        ProductRequest request = ProductDataBuilder.buildProductRequest();

        assertThrows(ResponseStatusException.class, () ->
                productService.createProduct(image, request, loggedUser.getEmail()));

        verify(productRepository, times(0)).save(any());
    }

    @DisplayName("Create new product - category not found")
    @Test
    void createProductCategoryNotFound() {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", "", "image content".getBytes());
        ProductRequest request = ProductDataBuilder.buildProductRequest();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(loggedUser));
        when(categoryRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResponseStatusException.class, () ->
                productService.createProduct(image, request, loggedUser.getEmail()));

        verify(productRepository, times(0)).save(any());
    }

    @DisplayName("Create new product - success")
    @Test
    void createProduct() {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", "", "image content".getBytes());
        ProductRequest request = ProductDataBuilder.buildProductRequest();
        String imageUrl = "http://www.cloudinary.com/images/1231312";

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(loggedUser));
        when(imageService.uploadImage(any())).thenReturn(imageUrl);
        when(productRepository.save(any())).thenReturn(ProductDataBuilder.buildProduct());
        when(categoryRepository.existsById(anyInt())).thenReturn(true);

        Integer productId = productService.createProduct(image, request, loggedUser.getEmail());

        verify(productRepository).save(productAc.capture());

        Product productBeforeSave = productAc.getValue();

        // assert Product properties before save
        assertNull(productBeforeSave.getId());
        assertEquals(imageUrl, productBeforeSave.getImageUrl());
        assertEquals(request.name(), productBeforeSave.getName());
        assertEquals(request.description(), productBeforeSave.getDescription());
        assertEquals(request.price(), productBeforeSave.getPrice());
        assertEquals(request.quantity(), productBeforeSave.getQuantity());
        assertNotNull(productBeforeSave.getSoldByUser());

        // assert product ID returned by product service
        assertNotNull(productId);
    }

    @DisplayName("Get product by id - not found")
    @Test
    void getProductByIdNotFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> productService.getProductById(1));
    }

    @DisplayName("Get product by id - success")
    @Test
    void getProductById() {
        Product productDb = ProductDataBuilder.buildProduct();

        when(productRepository.findById(anyInt())).thenReturn(Optional.of(productDb));

        ProductResponse response =  productService.getProductById(productDb.getId());

        assertEquals(productDb.getId(), response.id());
        assertEquals(productDb.getImageUrl(), response.imageUrl());
        assertEquals(productDb.getName(), response.name());
        assertEquals(productDb.getDescription(), response.description());
        assertEquals(productDb.getPrice(), response.price());
        assertEquals(productDb.getQuantity(), response.quantity());
        assertNotNull(response.soldByUser());
    }

    @DisplayName("Get list of products by category id - success")
    @Test
    void getProductsByCategoryId() {
        Product productDb = ProductDataBuilder.buildProduct();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(productDb), pageable, 1);

        when(productRepository.findByCategory_IdOrderByIdDesc(anyInt(), any())).thenReturn(productPage);

        Page<ProductResponse> response = productService.getProductsByCategoryId(productDb.getCategory().getId(),
                pageable);

        assertEquals(productPage.getContent().size(), response.getContent().size());
    }
}