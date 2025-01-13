package com.grupo1.pos.service;

import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetalleVentaService {
    List<DetalleVenta> findAll();

    Optional<DetalleVenta> findById(Long id);

    DetalleVenta save(DetalleVenta detalleVenta);

    @Query("SELECT dv FROM DetalleVenta dv JOIN FETCH dv.producto WHERE dv.venta.id = :venta_id")
    List<DetalleVenta> findDetallesByVentaId(@Param("ventaId") Long ventaId);

    void deleteById(Long id);
}
