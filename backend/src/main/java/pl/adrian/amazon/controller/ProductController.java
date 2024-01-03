package pl.adrian.amazon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.adrian.amazon.dto.ProductRequest;
import pl.adrian.amazon.security.AuthenticationFacade;
import pl.adrian.amazon.service.ProductService;

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
}
