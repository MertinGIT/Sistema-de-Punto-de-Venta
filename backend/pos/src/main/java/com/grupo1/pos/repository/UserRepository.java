package com.grupo1.pos.repository;

import com.grupo1.pos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    // Método para encontrar un usuario por su email
    Usuario findByEmail(String email);
}
