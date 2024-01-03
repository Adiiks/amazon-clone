package pl.adrian.amazon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.adrian.amazon.converter.ProductConverter;
import pl.adrian.amazon.data.ProductDataBuilder;
import pl.adrian.amazon.data.UserDataBuilder;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.entity.Product;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.repository.ProductRepository;
import pl.adrian.amazon.repository.UserRepository;
import pl.adrian.amazon.utils.ImageValidator;

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

    ImageValidator imageValidator = new ImageValidator();

    ProductConverter productConverter = new ProductConverter();

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
                productConverter
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

    @DisplayName("Create new product - success")
    @Test
    void createProduct() {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", "", "image content".getBytes());
        ProductRequest request = ProductDataBuilder.buildProductRequest();
        String imageUrl = "http://www.cloudinary.com/images/1231312";

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(loggedUser));
        when(imageService.uploadImage(any())).thenReturn(imageUrl);
        when(productRepository.save(any())).thenReturn(ProductDataBuilder.buildProduct());

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
}