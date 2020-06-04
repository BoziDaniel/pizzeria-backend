package com.codecool.pizzabackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:3000")
//
//public class AuthController {
//    private final AuthenticationManager authenticationManager;
//
//    private final JwtTokenServices jwtTokenServices;
//
//    public AuthController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices, AppUserRepository users) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenServices = jwtTokenServices;
//    }
//
//
//    @PreAuthorize("permitAll()")
//    @PostMapping("/login")
//    public String Login(@RequestBody  ) {
//        return "login";
//    }
//
//}
