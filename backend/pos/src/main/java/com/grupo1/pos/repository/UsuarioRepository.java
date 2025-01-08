package com.grupo1.pos.repository;

import com.grupo1.pos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // You can add custom queries if needed
    Usuario findByEmail(String email);
}