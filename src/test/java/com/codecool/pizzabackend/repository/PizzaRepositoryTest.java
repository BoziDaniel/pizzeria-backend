package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Pizza;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PizzaRepositoryTest {
    private void createPizzas(int numOfPizzas) {
        for (int i = 0; i < numOfPizzas; i++) {
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
    }
    @Autowired
    private PizzaRepository pizzaRepository;
    @Test
    void testGetNumberOfPizzas() {
        assertEquals(0, pizzaRepository.getNumberOfPizzas());
        createPizzas(20);
        assertEquals(20, pizzaRepository.getNumberOfPizzas());
    }

    @Test
    void testGetPaginatedPizzas() {
        createPizzas(31);
        assertEquals(10, pizzaRepository.getPaginatedPizzas(1).size());
        assertEquals(1, pizzaRepository.getPaginatedPizzas(4).size());
        assertEquals( "pizza_30" ,pizzaRepository.getPaginatedPizzas(4).get(0).getName());

    }
}