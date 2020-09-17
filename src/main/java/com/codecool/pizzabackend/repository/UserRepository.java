package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.controller.dto.UserForManagementDTO;
import com.codecool.pizzabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long Id);

    Optional<User> getAppUserByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

//    @Query(value = "SELECT u FROM User WHERE u.roles = :roles LIMIT (?1*10-10),10")
//    List<UserForManagementDTO> getPaginatedUsers(Integer page, HashSet<String> roles);
}

//    etPaginatedUsers(page, role)