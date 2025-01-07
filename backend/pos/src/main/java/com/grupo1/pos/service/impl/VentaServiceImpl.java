package com.grupo1.pos.service.impl;

import com.grupo1.pos.model.Venta;
import com.grupo1.pos.repository.VentaRepository;
import com.grupo1.pos.service.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService {
    private final VentaRepository ventaRepository;

    @Autowired
    public VentaServiceImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> getVentas(){
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> getVentaById(Long id){
        return ventaRepository.findById(id);
    }

    @Override
    public List<Venta> getVentasByFechaBetween(Date fechaInicial, Date fechaFinal){
        return ventaRepository.findVentasByFechaBetween(fechaInicial, fechaFinal);
    }

    @Override
    public List<Venta> getVentasByMetodoPago(String metodoPago){
        return ventaRepository.findVentasByMetodoPago(metodoPago);
    }

    @Override
    @Transactional
    public Venta agregarVenta(Venta venta){
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional
    public Venta actualizarVenta(Long id, Venta venta){
        Optional<Venta> ventaExistente = ventaRepository.findById(id);

        if(ventaExistente.isPresent()){
            Venta ventaActualizado = ventaExistente.get();
            ventaActualizado.setFecha(venta.getFecha());
            ventaActualizado.setUsuario(venta.getUsuario());
            ventaActualizado.setMontoTotal(venta.getMontoTotal());
            ventaActualizado.setMetodoPago(venta.getMetodoPago());
            ventaActualizado.setDetalles(venta.getDetalles());
            return ventaRepository.save(ventaActualizado);
        } else {
            throw new RuntimeException("No se encontró la venta");
        }
    }

    @Override
    @Transactional
    public void eliminarVenta(Long id) { ventaRepository.deleteById(id); }
}
