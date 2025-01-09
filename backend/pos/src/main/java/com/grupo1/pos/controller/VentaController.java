package com.grupo1.pos.controller;

import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.Venta;
import com.grupo1.pos.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    @Autowired
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Obtener todas los ventas
    @GetMapping
    public ResponseEntity<List<VentaDTO>> getVentas() {
        List<VentaDTO> ventas = ventaService.getVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> getVentaById(@PathVariable Long id) {
        Optional<VentaDTO> venta = ventaService.getVentaById(id);
        return venta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    // Agregar una nueva venta
    @PostMapping
    public ResponseEntity<VentaDTO> agregarVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        VentaDTO nuevaVenta = ventaService.agregarVenta(ventaDTO);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    // Actualizar una venta existente
    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        try {
            VentaDTO ventaActualizada = ventaService.actualizarVenta(id, ventaDTO);
            return new ResponseEntity<>(ventaActualizada, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        try {
            ventaService.eliminarVenta(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
