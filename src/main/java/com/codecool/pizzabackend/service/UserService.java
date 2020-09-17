package com.codecool.pizzabackend.service;


import com.codecool.pizzabackend.controller.dto.UserForManagementDTO;
import com.codecool.pizzabackend.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @PersistenceContext
    public EntityManager entityManager;

    public void getPaginatedUsers(Integer page, String role){
        //Set<String> roles = new HashSet<>(Arrays.asList(role));
        List<UserForManagementDTO> users = entityManager.createQuery("Select u as username" +
                " from User u join u.roles us").getResultList();
        for(UserForManagementDTO user : users){
            UserForManagementDTO userForManagementDTO =(UserForManagementDTO)user;
            System.out.println(userForManagementDTO.toString());
        }

    }
}
    //SELECT c, p.name FROM Country c JOIN c.capital p
