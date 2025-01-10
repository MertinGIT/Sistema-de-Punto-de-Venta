package com.grupo1.pos.util;

import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.model.Venta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-09T13:40:38-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class VentaMapperImpl implements VentaMapper {

    @Override
    public Venta toEntity(VentaDTO ventaDTO) {
        if ( ventaDTO == null ) {
            return null;
        }

        Venta venta = new Venta();

        venta.setUsuario( map( ventaDTO.getUsuarioId() ) );
        venta.setId( ventaDTO.getId() );
        venta.setFecha( ventaDTO.getFecha() );
        venta.setMontoTotal( ventaDTO.getMontoTotal() );
        venta.setMetodoPago( ventaDTO.getMetodoPago() );

        return venta;
    }

    @Override
    public Usuario map(Long usuarioId) {
        if ( usuarioId == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        return usuario;
    }

    @Override
    public VentaDTO toDTO(Venta venta) {
        if ( venta == null ) {
            return null;
        }

        VentaDTO ventaDTO = new VentaDTO();

        ventaDTO.setId( venta.getId() );
        ventaDTO.setFecha( venta.getFecha() );
        ventaDTO.setMontoTotal( venta.getMontoTotal() );
        ventaDTO.setMetodoPago( venta.getMetodoPago() );

        return ventaDTO;
    }
}
