package com.codecool.pizzabackend;

import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class PizzaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaBackendApplication.class, args);
    }

    @Autowired
    private PizzaRepository pizzaRepository;


    @Bean
    @Profile("production")
    public CommandLineRunner init() {

        return args -> {
            Pizza pizza = Pizza.builder()
                    .name("Testpizza")
                    .description("tastes fckin goooood")
                    .build();
            pizzaRepository.save(pizza);
        };

    }
}
