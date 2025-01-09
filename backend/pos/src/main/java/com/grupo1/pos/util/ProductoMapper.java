package com.grupo1.pos.util;

import com.grupo1.pos.dto.ProductoDTO;
import com.grupo1.pos.model.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoDTO toDTO(Producto producto);

    Producto toEntity(ProductoDTO productoDTO);
}
