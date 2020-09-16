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

    private PizzaRepository pizzaRepository;
    private OrderrRepository orderrRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    public DbInitializer(PizzaRepository pizzaRepository,
                         OrderrRepository orderrRepository,
                         UserRepository userRepository,
                         AddressRepository addressRepository) {
        this.pizzaRepository = pizzaRepository;
        this.orderrRepository = orderrRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

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
                .name("Anna")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_CUSTOMER")
                .email("customer1@gmail.com")
                .phoneNumber("06-30-111-1111")
                .build();
        userRepository.save(customer);

        Customer customer1 = Customer.builder()
                .username("customer1")
                .name("Péter")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_CUSTOMER")
                .phoneNumber("06-30-222-2222")
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
                .name("Józsi")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_COOK")
                .assignedOrder(inprogressOrder)
                .assignedOrder(readyOrder)
                .assignedOrder(indeliveryOrder)
                .assignedOrder(deliveredOrder)
                .phoneNumber("06-30-333-3333")
                .email("cook1@gmail.com")
                .build();
        userRepository.save(cook);

        Cook cook2 = Cook.builder()
                .username("cook2")
                .name("Laci")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_COOK")
                .phoneNumber("06-30-444-4444")
                .email("cook2@gmail.com")
                .build();
        userRepository.save(cook2);

        Manager manager = Manager.builder()
                .username("manager")
                .name("Béla")
                .role("ROLE_MANAGER")
                .password(passwordEncoder.encode("pass"))
                .phoneNumber("06-30-555-5555")
                .email("manager.LIKEABOSS@gmail.com")
                .build();
        userRepository.save(manager);
        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("deliveryGuy")
                .name("Pista")
                .password(passwordEncoder.encode("pass"))
                .role("ROLE_DELIVERYGUY")
                .phoneNumber("06-30-666-6666")
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
