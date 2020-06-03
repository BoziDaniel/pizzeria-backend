package com.codecool.pizzabackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenServices jwtTokenServices;

    //    @PreAuthorize() megnéz
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    public SecurityConfig(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET, "/pizzas/**").permitAll()// allowed by anyone
                .antMatchers(HttpMethod.GET, "/order/active/**").authenticated()
//                .antMatchers(HttpMethod.POST, "/order/")
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);

    }
}
