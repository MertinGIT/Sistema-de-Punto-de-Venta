package com.grupo1.pos.controller;

import com.grupo1.pos.dto.LoginRequestDTO;
import com.grupo1.pos.dto.LoginResponseDTO;
import com.grupo1.pos.dto.RegisterRequestDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SecurityConfig securityConfig;
    private final UsuarioRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(SecurityConfig securityConfig, UsuarioRepository userRepository) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        // Check credentials
        Usuario user = userRepository.findByEmail(request.getEmail());
        System.out.println("Usuario encontrado: " + user);
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: " + request.getPassword());
        System.out.println("User Password: "+ user.getPassword());
        System.out.println(passwordEncoder.matches(request.getPassword(), user.getPassword()));
        // Verificar si el usuario existe y si la contraseña es correcta
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Generate token
        String token = securityConfig.generateToken(user.getEmail(), "USER");
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.status(400).body("User already exists");
        }

        // Crear un nuevo usuario
        Usuario newUser = new Usuario();
        newUser.setNombre(request.getNombre());
        newUser.setRol(request.getRol());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Codificar la contraseña

        // Guardar en la base de datos
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

}
