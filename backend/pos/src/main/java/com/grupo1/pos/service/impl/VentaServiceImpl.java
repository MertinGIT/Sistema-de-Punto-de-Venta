package com.grupo1.pos.service.impl;

import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.util.VentaMapper;
import com.grupo1.pos.model.Venta;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.repository.VentaRepository;
import com.grupo1.pos.repository.UsuarioRepository;
import com.grupo1.pos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public VentaServiceImpl(VentaRepository ventaRepository, VentaMapper ventaMapper, UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<VentaDTO> getVentas() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VentaDTO> getVentaById(Long id) {
        return ventaRepository.findById(id)
                .map(ventaMapper::toDTO);
    }

    @Override
    public List<VentaDTO> getVentasByFechaBetween(Date fechaInicial, Date fechaFinal) {
        return ventaRepository.findVentasByFechaBetween(fechaInicial, fechaFinal).stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaDTO> getVentasByMetodoPago(String metodoPago) {
        return ventaRepository.findVentasByMetodoPago(metodoPago).stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDTO agregarVenta(VentaDTO ventaDTO) {
        Venta venta = ventaMapper.toEntity(ventaDTO);
        Venta savedVenta = ventaRepository.save(venta);
        return ventaMapper.toDTO(savedVenta);
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        Optional<Venta> existingVenta = ventaRepository.findById(id);
        if (existingVenta.isEmpty()) {
            throw new IllegalStateException("La venta con el ID " + id + " no existe");
        }

        Optional<Usuario> usuario = usuarioRepository.findById(ventaDTO.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new IllegalStateException("El usuario con el ID " + ventaDTO.getUsuarioId() + " no existe");
        }

        Venta venta = existingVenta.get();
        venta.setFecha(ventaDTO.getFecha());
        venta.setUsuario(usuario.get());
        venta.setMontoTotal(ventaDTO.getMontoTotal());
        venta.setMetodoPago(ventaDTO.getMetodoPago());
        Venta updatedVenta = ventaRepository.save(venta);
        return ventaMapper.toDTO(updatedVenta);
    }

    @Override
    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new IllegalStateException("La venta con el ID " + id + " no existe");
        }
        ventaRepository.deleteById(id);
    }
}
