package pl.adrian.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adrian.amazon.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}