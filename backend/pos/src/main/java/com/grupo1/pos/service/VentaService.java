package com.grupo1.pos.service;

import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.Venta;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    List<VentaDTO> getVentas();

    Optional<VentaDTO> getVentaById(Long id);

    List<VentaDTO> getVentasByFechaBetween(Date fechaInicial, Date fechaFinal);

    List<VentaDTO> getVentasByMetodoPago(String metodoPago);

    VentaDTO agregarVenta(VentaDTO ventaDTO);

    VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO);

    void eliminarVenta(Long id);

    Optional<Venta> findById(Long id);
}