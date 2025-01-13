package com.grupo1.pos.controller;

import com.grupo1.pos.dto.DetalleVentaDTO;
import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.model.Producto;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.model.Venta;
import com.grupo1.pos.repository.DetalleVentaRepository;
import com.grupo1.pos.repository.ProductoRepository;
import com.grupo1.pos.repository.UsuarioRepository;
import com.grupo1.pos.repository.VentaRepository;
import com.grupo1.pos.service.DetalleVentaService;
import com.grupo1.pos.service.ProductoService;
import com.grupo1.pos.service.VentaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")

@CrossOrigin(
        origins = {"http://localhost:4200/" }
)
public class VentaController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private final VentaService ventaService;
    private VentaRepository ventaRepository;
    private Venta venta = new Venta();
    private DetalleVentaService detalleVentaService;
    private ProductoRepository productoRepository;
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    public VentaController(DetalleVentaRepository detalleVentaRepository ,VentaService ventaService, VentaRepository ventaRepository, DetalleVentaService detalleVentaService, ProductoRepository productoRepository) {
        this.ventaService = ventaService;
        this.ventaRepository = ventaRepository;
        this.detalleVentaService = detalleVentaService;
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    // Obtener todas los ventas
    @GetMapping
    public ResponseEntity<List<VentaDTO>> getVentas(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuario_id");
        //  usuarioId = 7L; // Temporalmente forzado para pruebas
        System.out.println("USUARIO ID: " + usuarioId);
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
        usuarioId = 7L; // Temporalmente forzado para pruebas
        System.out.println("USUARIO ID: " + usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear y guardar la venta
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setUsuario(usuario);
        venta.setMontoTotal(ventaDTO.getMontoTotal());
        venta.setMetodoPago(ventaDTO.getMetodoPago());

        Venta ventaGuardada = ventaRepository.save(venta);

        // Procesar y guardar los detalles de la venta
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            System.out.println("Venta recibida: " + ventaDTO);
            // Validar que el producto exista
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            // Validar stock disponible
            if (producto.getStock() < detalleDTO.getCantidad()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Crear y guardar el detalle de la venta
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(detalleDTO.getCantidad());
            detalleVenta.setSubtotal(detalleDTO.getSubtotal());
            detalleVenta.setProducto(producto);
            detalleVenta.setVenta(ventaGuardada);
            detalleVentaService.save(detalleVenta);

            // Actualizar el stock del producto
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        return new ResponseEntity<>(ventaGuardada, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public VentaDTO actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // Validar y asignar campos
        if (ventaDTO.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        System.out.println("ID recibido: "+id);
        venta.setFecha(ventaDTO.getFecha());
        venta.setMetodoPago(ventaDTO.getMetodoPago());
        venta.setMontoTotal(ventaDTO.getMontoTotal());

        // Actualizar usuario si se proporciona
        if (ventaDTO.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(ventaDTO.getUsuarioId());
            venta.setUsuario(usuario);
        }
        // Eliminar detalles de la venta existente

        // Procesar y guardar los nuevos detalles de la venta
        System.out.println("ID pasado : " + id);
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            Long productoId = detalleDTO.getProductoId();
            System.out.println("EL id del producto: " + productoId);
            Producto producto = productoRepository.findById(productoId  )
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            // Validar stock disponible
            if (producto.getStock() < detalleDTO.getCantidad()) {
                return null;
            }


            // Crear y guardar el nuevo detalle de la venta
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setCantidad(detalleDTO.getCantidad());
            detalleVenta.setSubtotal(detalleDTO.getSubtotal());
            detalleVenta.setProducto(producto);
            detalleVenta.setVenta(venta);
            detalleVentaService.save(detalleVenta);

            // Actualizar el stock del producto
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        // Guardar los cambios en la venta
        Venta ventaActualizada = ventaRepository.save(venta);

        // Convertir a DTO y devolver
        VentaDTO ventaActualizadaDTO = new VentaDTO();
        ventaActualizadaDTO.setId(ventaActualizada.getId());
        ventaActualizadaDTO.setFecha(ventaActualizada.getFecha());
        ventaActualizadaDTO.setMetodoPago(ventaActualizada.getMetodoPago());
        ventaActualizadaDTO.setMontoTotal(ventaActualizada.getMontoTotal());
        ventaActualizadaDTO.setUsuarioId(ventaActualizada.getUsuario().getId());

        return ventaActualizadaDTO;
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
