package com.grupo1.pos.service.impl;

import com.grupo1.pos.model.Producto;
import com.grupo1.pos.repository.ProductoRepository;
import com.grupo1.pos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getProductos(){
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id){
        return productoRepository.findById(id);
    }

    @Override
    public Producto agregarProducto(Producto producto){
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto producto){
        Optional<Producto> productoExistente = productoRepository.findById(id);

        if(productoExistente.isPresent()){
            Producto productoActualizado = productoExistente.get();
            productoActualizado.setNombre(producto.getNombre());
            productoActualizado.setPrecio(producto.getPrecio());
            productoActualizado.setStock(producto.getStock());
            productoActualizado.setCodigoBarras(producto.getCodigoBarras());
            productoActualizado.setCategoria(producto.getCategoria());
            return productoRepository.save(productoActualizado);
        } else {
            throw new RuntimeException("No se encontró el producto");
        }
    }

}
