package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.entity.UserCredentials;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.security.JwtTokenServices;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();
            System.out.println(data.toString());
            // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenServices.createToken(username, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("roles", roles);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
