package com.codecool.pizzabackend.init;

import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.repository.AddressRepository;
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
    @Autowired
    private AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
    public void intializeDatabase() {
        //PizzaCretion
        for (int i = 1; i < 21; i++) {
            String pizzaName = "pizza_" + i;
            Pizza pizzaa = Pizza.builder()
                    .name(pizzaName)
                    .description("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam," +
                            " eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo."
                    )
                    .price(3000)
                    .build();
            pizzaRepository.save(pizzaa);
        }

        Pizza pizza = Pizza.builder()
                .name("Salami pizza")
                .description("tastes reeel goooood")
                .price(6000)
                .build();

        Pizza pizza2 = Pizza.builder()
                .name("Meatlover pizza")
                .description("tastes good ")
                .price(6000)
                .build();
        Pizza pizza3 = Pizza.builder()
                .name("Capricosa")
                .description("great pizza ")
                .price(8000)
                .build();
        Pizza pizza4 = Pizza.builder()
                .name("4 seasons")
                .description("Tasty")
                .price(47000)
                .build();
        Pizza pizza5 = Pizza.builder()
                .name("Hawai pizza")
                .description("Tasty")
                .price(47000)
                .build();
        Pizza pizza6 = Pizza.builder()
                .name("Songoku")
                .description("Tasty")
                .price(47000)
                .build();
        pizzaRepository.save(pizza);
        pizzaRepository.save(pizza2);
        pizzaRepository.save(pizza3);
        pizzaRepository.save(pizza4);
        pizzaRepository.save(pizza5);
        pizzaRepository.save(pizza6);

        Customer customer = Customer.builder()
                .username("customer")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_CUSTOMER")
                .email("customer1@gmail.com")
                .phoneNumber("0036709443402")
                .build();
        userRepository.save(customer);

        Customer customer1 = Customer.builder()
                .username("customer1")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_CUSTOMER")
                .phoneNumber("0036709443402")
                .email("customer2@gmail.com")
                .build();
        userRepository.save(customer1);
        Address address = Address.builder()
                .city("Budapest")
                .postalCode("1065")
                .street("Nagymező street")
                .streetNumber("44").build();
        addressRepository.save(address);
        Orderr customer1order = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 3);
                    put(pizza2, 3);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .address(address)
                .orderStatus(OrderStatus.IN_PROGRESS)
                .build();

        Orderr orderredOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 1);
                    put(pizza3, 3);
                    put(pizza4, 20);
                    put(pizza5, 8);
                    put(pizza6, 11);
                }})
                .customer(customer)
                .address(address)
                .orderStatus(OrderStatus.ORDERED)
                .build();

        Orderr inprogressOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 2);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .address(address)
                .orderStatus(OrderStatus.IN_PROGRESS)
                .build();

        Orderr readyOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 3);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .address(address)
                .orderStatus(OrderStatus.READY)
                .build();

        Orderr indeliveryOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 4);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .address(address)
                .orderStatus(OrderStatus.IN_DELIVERY)
                .build();

        Orderr deliveredOrder = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 5);
                    put(pizza3, 3);
                }})
                .customer(customer)
                .address(address)
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
                .phoneNumber("0036709443402")
                .email("cook1@gmail.com")
                .build();
        userRepository.save(cook);

        Cook cook2 = Cook.builder()
                .username("cook2")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_COOK")
                .phoneNumber("0036709443401")
                .email("cook2@gmail.com")
                .build();
        userRepository.save(cook2);

        Manager manager = Manager.builder()
                .username("manager")
                .role("ROLE_MANAGER")
                .password(passwordEncoder.encode("pass"))
                .phoneNumber("0036709443401")
                .email("manager.LIKEABOSS@gmail.com")
                .build();
        userRepository.save(manager);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("deliveryGuy")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_DELIVERYGUY")
                .phoneNumber("0036709443401")
                .email("deliveryGuy@gmail.com")
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
