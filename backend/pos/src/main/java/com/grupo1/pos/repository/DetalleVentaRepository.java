package com.grupo1.pos.repository;

import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findDetalleVentaByVentaId(Long ventaId);
    List<DetalleVenta> findDetalleVentaByProductoId(Long productoId);
    List<DetalleVenta> findByVentaId(Long ventaId);

    @Query("SELECT dv FROM DetalleVenta dv JOIN FETCH dv.producto WHERE dv.venta.id = :ventaId")
    List<DetalleVenta> findDetallesByVentaId(@Param("ventaId") Long ventaId);

    void deleteByVenta(Venta venta);
    @Modifying
    @Query("DELETE FROM DetalleVenta dv WHERE dv.venta.id = :id")
    void deleteById(@Param("id") Long id);

}