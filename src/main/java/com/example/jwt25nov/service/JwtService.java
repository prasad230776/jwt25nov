package com.example.jwt25nov.service;

import java.sql.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    String key = "tSYNgRJ1qzt7at8GDhCZYiJAHDrMGzYw";
    SecretKey secretKey;

    JwtService(){
        secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
    }
    public String getToken(String username, String roles){
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 10 *60*1000))
        .claim("roles", roles)
        .signWith(secretKey) // creating jwt builder
        .compact();
    }

    public Claims getClaims(String token){
        return (Claims)Jwts.parser()
        .verifyWith(secretKey)
        .build()    // creating jwt parser
        .parse(token) //check if the token is valid or not
        .getPayload();  
    }
    
}
