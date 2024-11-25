package com.example.jwt25nov.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User implements UserDetails{
    @Id
    @GeneratedValue
    Integer id;
    private String username;
    private String password;
    private String roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(roles.split(",")).stream()
        .map(role->new SimpleGrantedAuthority(role))
        .toList();
    }
}
