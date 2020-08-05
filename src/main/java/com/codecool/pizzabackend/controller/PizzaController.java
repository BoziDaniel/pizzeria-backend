package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.repository.PizzaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

    @GetMapping()
    public List<Pizza> getPaginatedPizzas(@RequestParam  Integer page) {
        LOGGER.info("Get request: /pizzas?page=" + page + " arrived");
        List<Pizza> pizzas = pizzaRepository.getPaginatedPizzas(page);
        LOGGER.info(" Get request: /pizzas?page=" + page + " processed. \n Return value will be: " + pizzas.toString());
        return pizzas;
    }
}
