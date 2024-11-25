package com.example.jwt25nov.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwt25nov.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with username :" + username));
    }
    
}
