package com.codecool.pizzabackend;

import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.repository.IncomingOrderRepository;
import com.codecool.pizzabackend.repository.PizzaRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;

@SpringBootApplication
public class PizzaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaBackendApplication.class, args);
    }

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IncomingOrderRepository incomingOrderRepository;
    @Autowired
    private UserRepository userRepository;

    @Bean
    @Profile("production")
    public CommandLineRunner init() {

        return args -> {
            for (int i = 1; i < 21; i++) {
                String pizzaName = "pizza_" + i;
                Pizza pizzaa = Pizza.builder()
                        .name(pizzaName)
                        .description("tastes reeel goooood")
                        .build();
                pizzaRepository.save(pizzaa);
            }

            Pizza pizza = Pizza.builder()
                    .name("Testpizza")
                    .description("tastes reeel goooood")
                    .build();

            Pizza pizza2 = Pizza.builder()
                    .name("Testpizza2")
                    .description("tastes reeel baaaaad ")
                    .build();

//            Pizza pizza3 = Pizza.builder()
//                    .name("Testpizza")
//                    .description("tastes reeel goooood")
//                    .build();

            IncomingOrder incomingOrder = IncomingOrder.builder()
                    .orderedPizzas(new HashMap<Pizza, Integer>(){{
                        put(pizza,1);
                        put(pizza2,5);
                    }})
                    .build();

            pizzaRepository.save(pizza);
            pizzaRepository.save(pizza2);
            incomingOrderRepository.save(incomingOrder);
            Customer customer = Customer.builder()
                    .username("customer")
                    .customerOrder(incomingOrder)
                    .build();
            userRepository.save(customer);
            Cook cook = Cook.builder()
                    .username("cook")
                    .assignedOrder(incomingOrder)
                    .build();
            userRepository.save(cook);
            Manager manager = Manager.builder()
                    .username("manager")
                    .build();
            userRepository.save(manager);
            DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                    .username("deliveryGuy")
                    .order(incomingOrder)
                    .build();
            userRepository.save(deliveryGuy);
//            DeliveryGuy badDeliveryGuy = DeliveryGuy.builder()
//                    .username("deliveryGuy")
//                    .order(incomingOrder)
//                    .build();
//            userRepository.save(badDeliveryGuy);
        };

    }
}
