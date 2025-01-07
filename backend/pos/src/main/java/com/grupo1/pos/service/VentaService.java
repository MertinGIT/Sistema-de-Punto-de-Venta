package com.grupo1.pos.service;

import com.grupo1.pos.model.Venta;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    List<Venta> getVentas();

    Optional<Venta> getVentaById(Long id);

    List<Venta> getVentasByFechaBetween(Date fechaInicial, Date fechaFinal);

    List<Venta> getVentasByMetodoPago(String metodoPago);

    Venta agregarVenta(Venta venta);

    Venta actualizarVenta(Long id, Venta venta);

    void eliminarVenta(Long id);
}