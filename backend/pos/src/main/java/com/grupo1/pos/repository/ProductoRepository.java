package com.grupo1.pos.repository;

import com.grupo1.pos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByNombre(String nombre);
    Producto findByCodigoBarras(String codigoBarras);
}
