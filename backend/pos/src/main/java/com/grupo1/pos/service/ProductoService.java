package com.grupo1.pos.service;

import com.grupo1.pos.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<ProductoDTO> getProductos(); // Cambiado para retornar una lista de DTOs

    ProductoDTO getProductoByNombre(String nombre); // Cambiado para retornar un DTO

    ProductoDTO getProductoByCodigoBarra(String codigoBarra); // Cambiado para retornar un DTO

    Optional<ProductoDTO> getProductoById(Long id); // Cambiado para retornar un Optional de DTO

    ProductoDTO agregarProducto(ProductoDTO productoDTO); // Recibe y retorna un DTO

    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO); // Recibe un DTO para actualizar

    void eliminarProducto(Long id);
}
