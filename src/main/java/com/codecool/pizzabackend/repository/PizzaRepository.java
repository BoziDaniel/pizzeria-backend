package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    Pizza getPizzaById(Long Id);

    @Query(value = "SELECT COUNT(ID) FROM Pizza ", nativeQuery = true)
    Integer getNumberOfPizzas();

    @Query(value = "SELECT * FROM Pizza LIMIT (?1*10-10),10", nativeQuery = true)
    List<Pizza> getPaginatedPizzas(Integer page);
}
