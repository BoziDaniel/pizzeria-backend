package com.codecool.pizzabackend.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "role")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @GeneratedValue
    @Id
    private Long id;

    //@Column(nullable = false, unique = true)
    private String username;

    private String name;
}
