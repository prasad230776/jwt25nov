package com.example.jwt25nov.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwt25nov.service.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MyFilter extends OncePerRequestFilter{

    JwtService jwtService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
       String authHeader = request.getHeader("Authorization");
       if(authHeader !=null && authHeader.startsWith("Bearer ")){
        //"Bearer gfwgfgdsgfdsgdfgfdgdgfdg"    if  Bearer auth type
        //"Basic dgjfgjajggfejf"        if Basic auth type
        Claims claims = jwtService.getClaims(authHeader.split(" ")[1]);
        //ctate authentication toen with authorisation details and save to security context holder for futher auth purposes
        String username = claims.getSubject();
        String roles =(String) claims.get("roles");
        var authorities = List.of(roles.split(",")).stream()
                           .map(SimpleGrantedAuthority::new).toList(); 
        var authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);

        authentication.setDetails(new WebAuthenticationDetails(request));
        //adding to securitycontext holder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
       }

       filterChain.doFilter(request, response);
    }
    
}
