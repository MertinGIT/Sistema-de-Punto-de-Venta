package com.grupo1.pos.util;

import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.model.Venta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    @Mapping(source = "usuarioId", target = "usuario")  // Solo mapeamos el usuarioId a usuario
    Venta toEntity(VentaDTO ventaDTO);
    Usuario map(Long usuarioId);

    VentaDTO toDTO(Venta venta);
}
