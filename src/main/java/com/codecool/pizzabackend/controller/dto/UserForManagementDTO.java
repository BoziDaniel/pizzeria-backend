package com.codecool.pizzabackend.controller.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForManagementDTO {
    private Long id;
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private Set<String> roles;
}
