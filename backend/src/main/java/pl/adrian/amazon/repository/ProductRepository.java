package pl.adrian.amazon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.adrian.amazon.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByCategory_IdOrderByIdDesc(Integer id, Pageable pageable);

    Page<Product> findByCategory_IdAndNameContainsIgnoreCase(Integer id, String name, Pageable pageable);

    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);
}