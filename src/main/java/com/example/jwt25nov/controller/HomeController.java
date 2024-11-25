package com.example.jwt25nov.controller;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt25nov.model.User;
import com.example.jwt25nov.repository.UserRepository;
import com.example.jwt25nov.service.JwtService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class HomeController {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    @GetMapping("/")
    public String home(){
        return "welcome to root";
    }

    @GetMapping("/secured")
    public String secured(){
        return "welcome to secured path";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "created succesfully";
    }

    @GetMapping("/user-path")
    public String userPath(){
        return "user path info";
    }

    @GetMapping("/admin-path")
    public String adminPath(){
        return "admin path info";
    }

    @PostMapping("/login")
    public String getToken(@RequestBody User user){
        var token = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        var authToken = authenticationManager.authenticate(token);
        if(authToken.isAuthenticated()){
            var authDetails = authToken.getAuthorities();
            var roles = authDetails.stream()
            .map(auth->auth.getAuthority())
            .collect(Collectors.joining(","));
            return jwtService.getToken(user.getUsername(), roles);
        }
        return "";
    }
    
}
