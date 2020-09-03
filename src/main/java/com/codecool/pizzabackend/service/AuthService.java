package com.codecool.pizzabackend.service;

import com.codecool.pizzabackend.entity.Customer;
import com.codecool.pizzabackend.entity.UserCredentials;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.security.JwtTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenServices jwtTokenServices;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenServices jwtTokenServices, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
        this.userRepository = userRepository;
    }

    private Map<Object, Object> createModel(String username, List<String> roles, String token) {
        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("roles", roles);
        model.put("token", token);
        return model;
    }

    public ResponseEntity login(UserCredentials data) {
        String username = data.getUsername();
        String password = data.getPassword();
        // authenticationManager.authenticate calls loadUserByUsername in CustomUserDetailsService
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtTokenServices.createToken(username, roles);

        Map<Object, Object> model = createModel(username, roles, token);
        return ResponseEntity.ok(model);
    }

    public void registerCustomer(Customer customer) {
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_CUSTOMER"));
        customer.setRoles(roles);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        userRepository.save(customer);
    }
}
