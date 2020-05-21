package com.codecool.pizzabackend;

import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.init.DbInitializer;
import com.codecool.pizzabackend.repository.OrderrRepository;
import com.codecool.pizzabackend.repository.PizzaRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.HashSet;

@SpringBootApplication
public class PizzaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaBackendApplication.class, args);
    }

    @Autowired
    DbInitializer dbInitializer;

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            dbInitializer.intializeDatabase();
        };

    }
}
