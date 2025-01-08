package com.grupo1.pos.service;

import com.grupo1.pos.model.DetalleVenta;
import java.util.List;
import java.util.Optional;

public interface DetalleVentaService {
    List<DetalleVenta> findAll();

    Optional<DetalleVenta> findById(Long id);

    DetalleVenta save(DetalleVenta detalleVenta);

    void deleteById(Long id);
}
