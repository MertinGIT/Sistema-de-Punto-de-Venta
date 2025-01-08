package com.grupo1.pos.repository;

import com.grupo1.pos.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findDetalleVentaByVentaId(Long ventaId, Pageable pageable);
    List<DetalleVenta> findDetalleVentaByProductoId(Long productoId, Pageable pageable);
}