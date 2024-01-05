package pl.adrian.amazon.service;

import org.springframework.web.multipart.MultipartFile;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.dto.ProductResponse;

public interface ProductService {

    Integer createProduct(MultipartFile image, ProductRequest product, String userEmail);

    ProductResponse getProductById(Integer productId);
}
