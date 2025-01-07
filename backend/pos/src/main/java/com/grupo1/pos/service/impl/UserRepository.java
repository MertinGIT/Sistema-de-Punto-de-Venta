package com.grupo1.pos.service.impl;

import com.grupo1.pos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    // You can add custom queries if needed
    Usuario findByEmail(String email);
}