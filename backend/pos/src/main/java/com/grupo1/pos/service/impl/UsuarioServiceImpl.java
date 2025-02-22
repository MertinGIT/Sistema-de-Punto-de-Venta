package com.grupo1.pos.service.impl;
import com.grupo1.pos.config.SecurityConfig;
import com.grupo1.pos.dto.UsuarioDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.repository.UsuarioRepository;
//import org.apache.catalina.security.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl {
    private final UsuarioRepository userRepository;
    private final SecurityConfig securityConfig;
    public UsuarioServiceImpl(UsuarioRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public List<Usuario> getUsuarios() {
        return userRepository.findAll();
    }

    public void registrarUsuario(UsuarioDTO usuarioDTO) {
        // Check if email already exists
        if (userRepository.findByEmail(usuarioDTO.getEmail()) != null) {
            throw new IllegalStateException("El email ya está registrado");
        }

        // Map DTO to Entity
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setRol(usuarioDTO.getRol());

        userRepository.save(usuario);
    }

    public void actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new IllegalStateException("El usuario con el ID " + id + " no existe");
        }

        Usuario usuario = optionalUsuario.get();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setRol(usuarioDTO.getRol());

        userRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("El usuario con el ID " + id + " no existe");
        }
        userRepository.deleteById(id);
    }

    public boolean isAdmin(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityConfig.getSecretKey())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        String role = claims.get("role", String.class);
        return "ADMINISTRADOR".equals(role);
    }
}
