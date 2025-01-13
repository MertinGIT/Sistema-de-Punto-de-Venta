package com.grupo1.pos.controller;

import com.grupo1.pos.dto.DetalleVentaDTO;
import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.model.Venta;
import com.grupo1.pos.service.DetalleVentaService;
import com.grupo1.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalle-venta")
@CrossOrigin(
        origins = {"http://localhost:4200/" }
)
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;
    private VentaService ventaService;
    @Autowired
    public DetalleVentaController(DetalleVentaService detalleVentaService, VentaService ventaService) {
        this.detalleVentaService = detalleVentaService;
        this.ventaService = ventaService;
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

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<?> getDetallesVentaByVentaId(@PathVariable Long ventaId) {
        // Usa el servicio para obtener la venta
        Optional<Venta> ventaOpt = ventaService.findById(ventaId);
        System.out.println("Detalles encontrados: " + ventaOpt);

        if (ventaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada");
        }

        Venta venta = ventaOpt.get(); // Si hay al menos un elemento

        List<DetalleVentaDTO> detalles = detalleVentaService.findDetallesByVentaId(ventaId)
                .stream()
                .map(detalle -> new DetalleVentaDTO(
                        detalle.getId(),
                        detalle.getCantidad(),
                        detalle.getSubtotal(),
                        detalle.getProducto().getId(),
                        detalle.getVenta().getId()

                ))
                .toList();

        // Construye el objeto de respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", venta.getUsuario().getNombre());
        response.put("metodoPago", venta.getMetodoPago());
        response.put("fecha", venta.getFecha());
        response.put("montoTotal", venta.getMontoTotal());
        response.put("detalles", detalles);

        return ResponseEntity.ok(response);
    }

}