package com.codecool.pizzabackend.init;

import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.repository.OrderrRepository;
import com.codecool.pizzabackend.repository.PizzaRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component
public class DbInitializer {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OrderrRepository orderrRepository;
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
    public void intializeDatabase() {
        //PizzaCretion
        for (int i = 1; i < 21; i++) {
            String pizzaName = "pizza_" + i;
            Pizza pizzaa = Pizza.builder()
                    .name(pizzaName)
                    .description("tastes reeel goooood")
                    .price(3000)
                    .build();
            pizzaRepository.save(pizzaa);
        }

        Pizza pizza = Pizza.builder()
                .name("Testpizza")
                .description("tastes reeel goooood")
                .price(6000)
                .build();

        Pizza pizza2 = Pizza.builder()
                .name("Testpizza2")
                .description("tastes good ")
                .price(6000)
                .build();
        Pizza pizza3 = Pizza.builder()
                .name("Testpizza3")
                .description("tastes reeel baaaaad ")
                .price(8000)
                .build();
        pizzaRepository.save(pizza);
        pizzaRepository.save(pizza2);
        pizzaRepository.save(pizza3);

        Customer customer = Customer.builder()
                .username("customer")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_CUSTOMER")
                .build();
        userRepository.save(customer);

        Customer customer1 = Customer.builder()
                .username("customer1")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_CUSTOMER")
                .build();
        userRepository.save(customer1);

        Orderr customer1order = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 3);
                    put(pizza2, 3);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .orderStatus(OrderStatus.IN_PROGRESS)
                .build();

        Orderr orderredOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 1);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .orderStatus(OrderStatus.ORDERED)
                .build();

        Orderr inprogressOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 2);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .orderStatus(OrderStatus.IN_PROGRESS)
                .build();

        Orderr readyOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 3);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .orderStatus(OrderStatus.READY)
                .build();

        Orderr indeliveryOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 4);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .orderStatus(OrderStatus.IN_DELIVERY)
                .build();

        Orderr deliveredOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 5);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .orderStatus(OrderStatus.DELIVERED)
                .build();

        orderrRepository.save(orderredOrder);
        orderrRepository.save(inprogressOrder);
        orderrRepository.save(readyOrder);
        orderrRepository.save(indeliveryOrder);
        orderrRepository.save(deliveredOrder);
        customer.setCustomerOrders(new HashSet<>() {{
            add(orderredOrder);
            add(inprogressOrder);
            add(readyOrder);
            add(indeliveryOrder);
            add(deliveredOrder);
        }});
        customer1.setCustomerOrders(new HashSet<>() {{
            add(customer1order);
        }});
        userRepository.save(customer1);
        userRepository.save(customer);

        Cook cook = Cook.builder()
                .username("cook")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_COOK")
                .assignedOrder(inprogressOrder)
                .assignedOrder(readyOrder)
                .assignedOrder(indeliveryOrder)
                .assignedOrder(deliveredOrder)
                .build();
        userRepository.save(cook);

        Manager manager = Manager.builder()
                .username("manager")
                .role("ROLE_MANAGER")
                .password(passwordEncoder.encode("pass"))
                .build();
        userRepository.save(manager);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("deliveryGuy")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_DELIVERYGUY")
                .assignedOrder(indeliveryOrder)
                .assignedOrder(deliveredOrder)
                .build();
        userRepository.save(deliveryGuy);
        inprogressOrder.setCook(cook);
        readyOrder.setCook(cook);
        indeliveryOrder.setCook(cook);
        deliveredOrder.setCook(cook);
        indeliveryOrder.setDeliveryGuy(deliveryGuy);
        deliveredOrder.setDeliveryGuy(deliveryGuy);
        orderrRepository.save(inprogressOrder);
        orderrRepository.save(readyOrder);
        orderrRepository.save(indeliveryOrder);
        orderrRepository.save(deliveredOrder);
    }
}
