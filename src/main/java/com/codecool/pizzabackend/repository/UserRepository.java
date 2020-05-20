package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Customer;
import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long Id);
}
