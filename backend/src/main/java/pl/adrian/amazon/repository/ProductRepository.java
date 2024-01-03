package pl.adrian.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adrian.amazon.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}