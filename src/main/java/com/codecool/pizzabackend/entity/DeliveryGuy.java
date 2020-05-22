package com.codecool.pizzabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@DiscriminatorValue("DeliveryGuy")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class DeliveryGuy extends User {
    @JoinTable(name = "delivery_guy_orders_to_deliver")
    @OneToMany
    @Singular
    private Set<Orderr> assignedOrders;
}
