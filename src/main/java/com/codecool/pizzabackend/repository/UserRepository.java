package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Customer;
import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.entity.User;
import com.codecool.pizzabackend.security.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long Id);

    @Query(value = "SELECT ROLE FROM USER WHERE ID=?1", nativeQuery = true)
    String getUserRoleByUserId(Long userId);

//    @Query(value = "SELECT ID, ROLE,IS_ACCOUNT_NON_EXPIRED,IS_ACCOUNT_NON_LOCKED, IS_CREDENTIALS_NON_EXPIRED, IS_ENABLED,NAME,PASSWORD,USERNAME, FROM USER WHERE USERNAME=?1")
//    Optional<UserCredential> findUserByUsername(String username);

    Optional<UserCredential> getUserByUsername(String userName);
}
