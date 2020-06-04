package com.codecool.pizzabackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Manager")
@Data
@AllArgsConstructor
//@NoArgsConstructor
@SuperBuilder
public class Manager extends User {

}
