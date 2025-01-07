package com.grupo1.pos.repository;

import com.grupo1.pos.model.Venta;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findVentasByFechaBetween(Date fechaDesde, Date fechaHasta);

    List<Venta> findVentasByMetodoPago(String metodoPago);
}
