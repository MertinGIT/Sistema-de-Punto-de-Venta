package com.grupo1.pos.config;

import com.grupo1.pos.controller.JwtAuthorizationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecretKey getSecretKey() {
        return secretKey;
    }

    private  SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secret key for HS256
    private static final long EXPIRATION_TIME = 86400000L; // 1 day expiration

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF
                .authorizeRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll() // Public routes
                        .requestMatchers(HttpMethod.PUT, "/auth/**").hasAuthority("ADMINISTRADOR") // Admin only for PUT
                        .requestMatchers(HttpMethod.DELETE, "/auth/**").hasAuthority("ADMINISTRADOR") // Admin only for DELETE
                        .anyRequest().authenticated() // Other requests require authentication
                )
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter before authentication filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt for password encoding
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(); // JWT authorization filter
    }

    // Method to generate JWT token
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add role to claims

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) // Use the secret key for signing
                .compact();
    }

    // Method to validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token); // Use parserBuilder
            return true; // Token is valid
        } catch (Exception e) {
            return false; // Token is invalid
        }
    }

    // Method to extract email from the token
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }
}