package com.grupo1.pos.util;

import com.grupo1.pos.dto.DetalleVentaDTO;
import com.grupo1.pos.model.DetalleVenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

    @Mapping(source = "ventaId", target = "venta.id")  // Mapea el ID de la venta
    @Mapping(source = "productoId", target = "producto.id")  // Mapea el ID del producto
    DetalleVenta toEntity(DetalleVentaDTO detalleVentaDTO);

    @Mapping(source = "venta.id", target = "ventaId")  // Mapea el objeto venta al campo ventaId
    @Mapping(source = "producto.id", target = "productoId")  // Mapea el objeto producto al campo productoId
    DetalleVentaDTO toDTO(DetalleVenta detalleVenta);
}
