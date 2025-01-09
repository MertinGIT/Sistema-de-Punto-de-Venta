package com.grupo1.pos.controller;

import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-venta")
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    @Autowired
    public DetalleVentaController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    // Obtener todos los detalles de venta
    @GetMapping
    public List<DetalleVenta> getAllDetalleVentas() {
        return detalleVentaService.findAll();
    }

    // Obtener un detalle de venta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> getDetalleVentaById(@PathVariable Long id) {
        Optional<DetalleVenta> detalleVenta = detalleVentaService.findById(id);
        return detalleVenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo detalle de venta
    @PostMapping
    public ResponseEntity<DetalleVenta> agregarDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        DetalleVenta savedDetalleVenta = detalleVentaService.save(detalleVenta);
        return new ResponseEntity<>(savedDetalleVenta, HttpStatus.CREATED);
    }

    // Actualizar un detalle de venta
    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> actualizarDetalleVenta(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        if (detalleVentaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        detalleVenta.setId(id);
        DetalleVenta updatedDetalleVenta = detalleVentaService.save(detalleVenta);
        return ResponseEntity.ok(updatedDetalleVenta);
    }

    // Eliminar un detalle de venta por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Long id) {
        if (detalleVentaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        detalleVentaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}