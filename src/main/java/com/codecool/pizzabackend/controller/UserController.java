package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.controller.dto.UserForManagementDTO;
import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.entity.User;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping()
    public void getPaginatedUsers(@RequestParam  Integer page,@RequestParam String role) {
        LOGGER.info(String.format("\"Get request arrived to path: /users?page=%s&role=%s",page,role));
        //List<UserForManagementDTO> paginatedUsers =
        userService.getPaginatedUsers(page, role);
        //LOGGER.info(String.format("\"Get request /users?page=%s&role=%s processed. result: %s",page,role, paginatedUsers));
        //return paginatedUsers;
    }

    @GetMapping("/username/exists")
    public boolean isUserExistByName(@RequestParam("username") String username){
        LOGGER.info(String.format("Get request arrived to path: /users/username/exists?username=%s",username));
        boolean isUserWithNameExist = userRepository.existsByUsername(username);
        LOGGER.info(String.format("Get request users/username/exists?username=%s processed. result: %s",username, isUserWithNameExist));
        return isUserWithNameExist;
    }
    @GetMapping("/phoneNumber/exists")
    public boolean isPhoneNumberOccupied(@RequestParam("phoneNumber") String phoneNumber){
        LOGGER.info(String.format("Get request arrived to path: /users/exists?phoneNumber=%s", phoneNumber));
        boolean isPhoneNumberAlreadyInUse = userRepository.existsByPhoneNumber(phoneNumber);
        LOGGER.info(String.format("Get request /users/exists/%s processed. result: %s",phoneNumber, isPhoneNumberAlreadyInUse));
        return isPhoneNumberAlreadyInUse;
    }
    @GetMapping("/email/exists")
    public boolean isEmailOccupied(@RequestParam("email") String email){
        LOGGER.info(String.format("Get request arrived to path: /users/email/exists?email=%s", email));
        boolean isEmailAlreadyInUse = userRepository.existsByEmail(email);
        LOGGER.info(String.format("Get request /users/email/exists?%s processed. result: %s",email, isEmailAlreadyInUse));
        return isEmailAlreadyInUse;
    }
}
