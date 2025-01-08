package com.grupo1.pos.controller;

import com.grupo1.pos.controller.LoginRequestDTO;
import com.grupo1.pos.controller.LoginResponseDTO;
import com.grupo1.pos.controller.RegisterRequestDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.repository.UserRepository;
import com.grupo1.pos.service.impl.UsuarioService;
import com.grupo1.pos.controller.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;
    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(SecurityConfig securityConfig, UserRepository userRepository, UsuarioService usuarioService) {
        this.securityConfig = securityConfig;
        this.userRepository = userRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        // Buscar el usuario por email
        Usuario user = userRepository.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Generar un token
        String token = securityConfig.generateToken(user.getEmail(), user.getRol());
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

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody RegisterRequestDTO request, @RequestHeader("Authorization") String token) {
        // Verificar si el usuario tiene rol de ADMINISTRADOR
        System.out.println("ENtro al api");
        if (!usuarioService.isAdmin(token)) {
            return ResponseEntity.status(403).body("Access denied: Only administrators can update users");
        }

        // Buscar el usuario por ID
        Optional<Usuario> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Actualizar datos del usuario
        Usuario usuario = optionalUsuario.get();
        usuario.setNombre(request.getNombre());
        usuario.setRol(request.getRol());
        usuario.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(usuario);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        // Verificar si el usuario tiene rol de ADMINISTRADOR
        System.out.println("Token recibido en eliminarUsuario: " + token);
        if (!usuarioService.isAdmin(token)) {
            return ResponseEntity.status(403).body("Access denied: Only administrators can delete users");
        }

        // Verificar si el usuario existe
        Optional<Usuario> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Eliminar usuario
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
