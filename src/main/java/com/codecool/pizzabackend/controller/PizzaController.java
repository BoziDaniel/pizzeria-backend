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

    private PizzaRepository pizzaRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

    @Autowired
    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/numberOfPizzas")
    public Integer getNumberOfPizzas(){
        LOGGER.info("Get request: /pizzas/numberOfPizzas arrived");
        Integer numberOfPizzas = pizzaRepository.getNumberOfPizzas();
        LOGGER.info(" Get request: /pizzas/numberOfPizzas processed. \n Return value will be: " + numberOfPizzas);
        return numberOfPizzas;
    }

    @GetMapping()
    public List<Pizza> getPaginatedPizzas(@RequestParam  Integer page) {
        LOGGER.info("Get request: /pizzas?page=" + page + " arrived");
        List<Pizza> pizzas = pizzaRepository.getPaginatedPizzas(page);
        LOGGER.info(" Get request: /pizzas?page=" + page + " processed. \n Return value will be: " + pizzas.toString());
        return pizzas;
    }


}
