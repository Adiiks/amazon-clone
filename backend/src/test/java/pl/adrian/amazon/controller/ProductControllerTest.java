package pl.adrian.amazon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.adrian.amazon.data.ProductDataBuilder;
import pl.adrian.amazon.data.UserDataBuilder;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.entity.User;
import pl.adrian.amazon.security.AuthenticationFacade;
import pl.adrian.amazon.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Mock
    AuthenticationFacade authenticationFacade;

    ObjectMapper objectMapper = new ObjectMapper();

    User loggedUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new ValidationErrorControllerAdvice())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        loggedUser = UserDataBuilder.buildUser();
    }

    @DisplayName("Create product - body validation failed")
    @Test
    void createProductInvalidBody() throws Exception {
        ProductRequest request = new ProductRequest("", "", BigDecimal.ZERO, -1, null);
        MockMultipartFile image = new MockMultipartFile("image", "image content".getBytes());
        MockMultipartFile jsonRequest = new MockMultipartFile("request", "", MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsBytes(request));

        mockMvc.perform(multipart("/api/products")
                .file(image)
                .file(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(5)));

        verify(productService, times(0)).createProduct(any(), any(), anyString());
    }

    @DisplayName("Create product - success")
    @Test
    void createProduct() throws Exception {
        ProductRequest request = ProductDataBuilder.buildProductRequest();
        MockMultipartFile image = new MockMultipartFile("image", "image content".getBytes());
        MockMultipartFile jsonRequest = new MockMultipartFile("request", "", MediaType.APPLICATION_JSON.toString(),
                objectMapper.writeValueAsBytes(request));

        when(authenticationFacade.getEmail()).thenReturn(loggedUser.getEmail());

        mockMvc.perform(multipart("/api/products")
                        .file(image)
                        .file(jsonRequest))
                .andExpect(status().isOk());

        verify(productService).createProduct(any(), any(), anyString());
    }

    @DisplayName("Get product by id - success")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk());

        verify(productService).getProductById(anyInt());
    }

    @DisplayName("Get list of products based on category id without search param - success")
    @Test
    void getProductsByCategoryIdWithoutSearch() throws Exception {
        mockMvc.perform(get("/api/products/category/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());

        verify(productService).getProductsByCategoryId(anyInt(), any(), isNull());
    }

    @DisplayName("Get list of products based on category id with search param - success")
    @Test
    void getProductsByCategoryIdWithSearch() throws Exception {
        mockMvc.perform(get("/api/products/category/1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("search", "reacher"))
                .andExpect(status().isOk());

        verify(productService).getProductsByCategoryId(anyInt(), any(), anyString());
    }

    @DisplayName("Get products list without search param - success")
    @Test
    void getProductsWithoutSearch() throws Exception {
        mockMvc.perform(get("/api/products")
                    .param("page", "0")
                    .param("size", "10"))
                .andExpect(status().isOk());

        verify(productService).getProducts(isNull(), any());
    }

    @DisplayName("Get products list with search param - success")
    @Test
    void getProductsWithSearch() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("page", "0")
                        .param("size", "10")
                        .param("search", "Reacher"))
                .andExpect(status().isOk());

        verify(productService).getProducts(anyString(), any());
    }

    @DisplayName("Get list of products based on list of IDs")
    @Test
    void getProductsByIdsList() throws Exception {
        List<Integer> ids = List.of(1, 2, 3);

        mockMvc.perform(get("/api/products/ids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());

        verify(productService).getProductsByIdsList(anyList());
    }
}