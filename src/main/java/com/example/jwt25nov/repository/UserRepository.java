package com.example.jwt25nov.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt25nov.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    public Optional<User> findByUsername(String username);
}
