package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long Id);

    Optional<User> getAppUserByUsername(String username);
}
