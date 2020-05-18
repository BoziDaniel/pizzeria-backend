package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
