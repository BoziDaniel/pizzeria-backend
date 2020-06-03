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
@CrossOrigin(origins = "http://localhost:3000")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

    @GetMapping("/{page}")
    public List<Pizza> getPaginatedPizzas(@PathVariable("page") Integer page) {
        LOGGER.info("Get request: /pizzas/" + page + " arrived");
        List<Pizza> pizzas = pizzaRepository.getPaginatedPizzas(page);
        LOGGER.info(" Get request: /pizzas/" + page + " processed. \n Return value will be: " + pizzas.toString());
        return pizzas;
    }
}
