package com.codecool.pizzabackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IncomingOrder {
    @GeneratedValue
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ElementCollection
    @CollectionTable(name = "incomingOrder_pizza_mapping",
            joinColumns = {@JoinColumn(name = "incomingOrder_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "pizza_name")
    @Column(name = "quantity")
    @Singular

    Map<Pizza, Integer> orderedPizzas;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonManagedReference
    private Customer customer;
}
