package pl.adrian.amazon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.dto.ProductResponse;
import pl.adrian.amazon.security.AuthenticationFacade;
import pl.adrian.amazon.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AuthenticationFacade authenticationFacade;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public Integer createProduct(@RequestPart MultipartFile image, @Valid @RequestPart ProductRequest request) {
        return productService.createProduct(image, request, authenticationFacade.getEmail());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/category/{categoryId}")
    public Page<ProductResponse> getProductsByCategoryId(@PathVariable Integer categoryId, Pageable pageable,
                                                         @RequestParam(required = false) String search) {
        return productService.getProductsByCategoryId(categoryId, pageable, search);
    }
}
