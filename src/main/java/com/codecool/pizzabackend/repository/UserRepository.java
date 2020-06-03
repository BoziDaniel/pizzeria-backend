package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long Id);

    @Query(value = "SELECT ROLE FROM USER WHERE ID=?1", nativeQuery = true)
    String getUserRoleByUserId(Long userId);

    Optional<User> getAppUserByUsername(String username);
}
