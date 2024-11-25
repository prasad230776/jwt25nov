package com.example.jwt25nov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jwt25nov.filter.MyFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class MySecurityConfig {
    
    MyFilter myFilter;
    @Bean
    public SecurityFilterChain chain(HttpSecurity security) throws Exception{
        return security
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(auth->auth.requestMatchers("/", "/register", "/login")
        .permitAll()
        .requestMatchers("/user-path").hasAnyAuthority("user", "admin")
        .requestMatchers("/admin-path").hasAnyAuthority("admin")
        .anyRequest().authenticated()
        )
        .addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(handler->handler.authenticationEntryPoint(authEntryPoint())
        .accessDeniedHandler(accessDeniedhandler()))
        .httpBasic(Customizer.withDefaults())
        .build();
    }

    private AccessDeniedHandler accessDeniedhandler() {
       return(request, response, ex)->{
        response.setStatus(403);
       };
    }

    private AuthenticationEntryPoint authEntryPoint() {
        return (request, response, ex)->{
            response.setStatus(401);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
