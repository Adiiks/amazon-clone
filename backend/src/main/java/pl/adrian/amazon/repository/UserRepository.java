package pl.adrian.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.adrian.amazon.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}