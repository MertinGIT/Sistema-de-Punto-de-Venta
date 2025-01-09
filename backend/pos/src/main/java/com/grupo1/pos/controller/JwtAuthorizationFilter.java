package com.grupo1.pos.controller;

import com.grupo1.pos.config.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String jwtToken = getJwtFromRequest(request);

        if (jwtToken != null && securityConfig.validateToken(jwtToken)) {
            System.out.println("El token es: " + jwtToken);
            Claims claims = extractClaims(jwtToken);
            setUpSpringAuthentication(claims);
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HEADER);
        System.out.println("El header es "+header);
        if (header != null && header.startsWith(PREFIX)) {
            return header.substring(PREFIX.length());
        }
        return null;
    }

    private Claims extractClaims(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(securityConfig.getSecretKey()).build().parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        // Extraer el rol del JWT (ahora es un String, no una lista)
        String authority = (String) claims.get("role");
        System.out.println("El authority es "+authority);
        // Crear la autenticación con el email y el rol
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                claims.getSubject(), null, // Establecer el email como el nombre de usuario
                List.of(new SimpleGrantedAuthority(authority)) // Convertir el rol a autoridad
        );

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}