package com.grupo1.pos.controller;

import com.grupo1.pos.dto.LoginRequestDTO;
import com.grupo1.pos.dto.LoginResponseDTO;
import com.grupo1.pos.dto.RegisterRequestDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.repository.UsuarioRepository;
import com.grupo1.pos.service.impl.UsuarioServiceImpl;
import com.grupo1.pos.config.SecurityConfig;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(
        origins = {"http://localhost:4200/"}
)
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioServiceImpl usuarioService;
    private final SecurityConfig securityConfig;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthController(SecurityConfig securityConfig, UsuarioRepository usuarioRepository, UsuarioServiceImpl usuarioService) {
        this.securityConfig = securityConfig;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }
    @GetMapping({"/listar"})
    public ResponseEntity<?> listarUsuarios() {
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request, HttpSession session) {
        // Buscar el usuario por email
        Usuario user = usuarioRepository.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Guardar el id del usuario en la sesión
        session.setAttribute("usuario_id", user.getId());

        // Generar un token
        String token = securityConfig.generateToken(user.getEmail(), user.getRol());

        // Incluir el idUsuario en la respuesta
        return ResponseEntity.ok(new LoginResponseDTO(token, user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.status(400).body("User already exists");
        }

        // Crear un nuevo usuario
        Usuario newUser = new Usuario();
        newUser.setNombre(request.getNombre());
        newUser.setRol(request.getRol());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // Codificar la contraseña

        // Guardar en la base de datos
        usuarioRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody RegisterRequestDTO request/*, @RequestHeader("Authorization") String token*/) {
        // Verificar si el usuario tiene rol de ADMINISTRADOR
        //System.out.println("ENtro al api");
       /* if (!usuarioService.isAdmin(token)) {
            return ResponseEntity.status(403).body("Access denied: Only administrators can update users");
        }*/

        // Buscar el usuario por ID
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
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

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("User registered successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id/*, @RequestHeader("Authorization") String token*/) {
        // Verificar si el usuario tiene rol de ADMINISTRADOR
       /* System.out.println("Token recibido en eliminarUsuario: " + token);
        if (!usuarioService.isAdmin(token)) {
            return ResponseEntity.status(403).body("Access denied: Only administrators can delete users");
        }
*/
        // Verificar si el usuario existe
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Eliminar usuario
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
