package com.grupo1.pos.service.impl;

import com.grupo1.pos.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> getProductos();

    Optional<Producto> getProductoById(Long id);

    Producto agregarProducto(Producto producto);

    Producto actualizarProducto(Long id, Producto producto);

    void eliminarProducto(Long id);
}