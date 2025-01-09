package com.grupo1.pos.repository;

import com.grupo1.pos.model.Usuario;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // You can add custom queries if needed
    Usuario findByEmail(String email);
}