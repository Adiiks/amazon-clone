package pl.adrian.amazon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    Integer createProduct(MultipartFile image, ProductRequest product, String userEmail);

    ProductResponse getProductById(Integer productId);

    Page<ProductResponse> getProductsByCategoryId(Integer categoryId, Pageable pageable, String search);

    Page<ProductResponse> getProducts(String search, Pageable pageable);

    List<ProductResponse> getProductsByIdsList(List<Integer> ids);
}
