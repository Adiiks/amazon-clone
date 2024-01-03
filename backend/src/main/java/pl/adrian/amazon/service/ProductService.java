package pl.adrian.amazon.service;

import org.springframework.web.multipart.MultipartFile;
import pl.adrian.amazon.dto.ProductRequest;

public interface ProductService {

    Integer createProduct(MultipartFile image, ProductRequest product, String userEmail);
}
