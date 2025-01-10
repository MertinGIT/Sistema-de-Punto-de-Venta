package com.grupo1.pos.controller;

import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.model.Venta;
import com.grupo1.pos.repository.UsuarioRepository;
import com.grupo1.pos.repository.VentaRepository;
import com.grupo1.pos.service.VentaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final VentaService ventaService;
    private VentaRepository ventaRepository;
    private Venta venta = new Venta();
    @Autowired
    public VentaController(VentaService ventaService, VentaRepository ventaRepository) {
        this.ventaService = ventaService;
        this.ventaRepository = ventaRepository;
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


    @PostMapping()
    public ResponseEntity<?> registrarVenta(HttpSession session, @RequestBody VentaDTO ventaDTO) {
        Long usuarioId = (Long) session.getAttribute("usuario_id");
        System.out.println("USUARIO ID: " + usuarioId);
        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        // Buscar el usuario en la base de datos usando el usuarioId
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        System.out.println("Usuario: " + usuario.getNombre());
        // Crear una nueva instancia de Venta y establecer los valores
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setUsuario(usuario);  // Establecer el usuario encontrado
        venta.setMontoTotal(ventaDTO.getMontoTotal());
        venta.setMetodoPago(ventaDTO.getMetodoPago());

        // Guardar la venta utilizando el servicio
        ventaRepository.save(venta);

        // Retornar la respuesta con la venta guardada
        return new ResponseEntity<>(venta, HttpStatus.CREATED);
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
