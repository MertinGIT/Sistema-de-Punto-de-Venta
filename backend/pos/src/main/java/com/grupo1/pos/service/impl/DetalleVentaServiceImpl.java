package com.grupo1.pos.service.impl;

import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.repository.DetalleVentaRepository;
import com.grupo1.pos.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {


    private final DetalleVentaRepository detalleVentaRepository;

    @Autowired
    public DetalleVentaServiceImpl(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    public List<DetalleVenta> findDetallesByVentaId(Long ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }
    @Override
    public List<DetalleVenta> findAll() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public Optional<DetalleVenta> findById(Long id) {
        return detalleVentaRepository.findById(id);
    }

    @Override
    public DetalleVenta save(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}
