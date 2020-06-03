package com.codecool.pizzabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorColumn(name = "usertype")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String name;

    @JsonIgnore
    private  String password;
    @Transient
    private Set<SimpleGrantedAuthority> grantedAuthorities;


    private String role;

    private  boolean isAccountNonExpired;
    private  boolean isAccountNonLocked;
    private  boolean isCredentialsNonExpired;
    private  boolean isEnabled;

    public User(String username,
                String password,
                String name,
                String role,
                Set<SimpleGrantedAuthority> grantedAuthorities,
                boolean isAccountNonExpired,
                boolean isAccountNonLocked,
                boolean isCredentialsNonExpired,
                boolean isEnabled
                           ){
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = role;
        if(grantedAuthorities!=null){
            this.grantedAuthorities = grantedAuthorities;
        }else {
            this.grantedAuthorities = new HashSet<>();
        }

        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        initializeGrantedAuthorities();
    }

    private void initializeGrantedAuthorities(){
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
