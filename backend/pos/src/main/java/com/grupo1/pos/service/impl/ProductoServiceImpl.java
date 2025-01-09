package com.grupo1.pos.service.impl;

import com.grupo1.pos.dto.ProductoDTO;
import com.grupo1.pos.util.ProductoMapper;
import com.grupo1.pos.model.Producto;
import com.grupo1.pos.repository.ProductoRepository;
import com.grupo1.pos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDTO> getProductos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)  // Usamos el mapper para convertir a DTO
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO getProductoByNombre(String nombre) {
        Producto producto = productoRepository.findByNombre(nombre);
        return producto != null ? productoMapper.toDTO(producto) : null;
    }

    @Override
    public ProductoDTO getProductoByCodigoBarra(String codigoBarra) {
        Producto producto = productoRepository.findByCodigoBarras(codigoBarra);
        return producto != null ? productoMapper.toDTO(producto) : null;
    }

    @Override
    public Optional<ProductoDTO> getProductoById(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDTO);  // Usamos el mapper para convertir a DTO
    }

    @Override
    public ProductoDTO agregarProducto(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);  // Convertimos el DTO a entidad
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(savedProducto);  // Convertimos la entidad guardada a DTO
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Optional<Producto> existingProducto = productoRepository.findById(id);
        if (existingProducto.isEmpty()) {
            throw new IllegalStateException("El producto con el ID " + id + " no existe");
        }

        Producto producto = existingProducto.get();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCodigoBarras(productoDTO.getCodigoBarras());
        producto.setCategoria(productoDTO.getCategoria());

        Producto updatedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(updatedProducto);  // Convertimos la entidad actualizada a DTO
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalStateException("El producto con el ID " + id + " no existe");
        }
        productoRepository.deleteById(id);
    }
}
