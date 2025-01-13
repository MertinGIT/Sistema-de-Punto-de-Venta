package com.grupo1.pos.util;

import com.grupo1.pos.dto.DetalleVentaDTO;
import com.grupo1.pos.dto.VentaDTO;
import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.model.Venta;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T01:49:08-0300",
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
        venta.setDetalles( detalleVentaDTOListToDetalleVentaList( ventaDTO.getDetalles() ) );

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

        ventaDTO.setDetalles( detalleVentaListToDetalleVentaDTOList( venta.getDetalles() ) );
        ventaDTO.setId( venta.getId() );
        ventaDTO.setFecha( venta.getFecha() );
        ventaDTO.setMontoTotal( venta.getMontoTotal() );
        ventaDTO.setMetodoPago( venta.getMetodoPago() );

        return ventaDTO;
    }

    protected DetalleVenta detalleVentaDTOToDetalleVenta(DetalleVentaDTO detalleVentaDTO) {
        if ( detalleVentaDTO == null ) {
            return null;
        }

        DetalleVenta detalleVenta = new DetalleVenta();

        detalleVenta.setId( detalleVentaDTO.getId() );
        detalleVenta.setCantidad( detalleVentaDTO.getCantidad() );
        detalleVenta.setSubtotal( detalleVentaDTO.getSubtotal() );

        return detalleVenta;
    }

    protected List<DetalleVenta> detalleVentaDTOListToDetalleVentaList(List<DetalleVentaDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<DetalleVenta> list1 = new ArrayList<DetalleVenta>( list.size() );
        for ( DetalleVentaDTO detalleVentaDTO : list ) {
            list1.add( detalleVentaDTOToDetalleVenta( detalleVentaDTO ) );
        }

        return list1;
    }

    protected DetalleVentaDTO detalleVentaToDetalleVentaDTO(DetalleVenta detalleVenta) {
        if ( detalleVenta == null ) {
            return null;
        }

        DetalleVentaDTO detalleVentaDTO = new DetalleVentaDTO();

        detalleVentaDTO.setId( detalleVenta.getId() );
        detalleVentaDTO.setCantidad( detalleVenta.getCantidad() );
        detalleVentaDTO.setSubtotal( detalleVenta.getSubtotal() );
        detalleVentaDTO.setIva10( detalleVenta.getIva10() );
        detalleVentaDTO.setIva5( detalleVenta.getIva5() );

        return detalleVentaDTO;
    }

    protected List<DetalleVentaDTO> detalleVentaListToDetalleVentaDTOList(List<DetalleVenta> list) {
        if ( list == null ) {
            return null;
        }

        List<DetalleVentaDTO> list1 = new ArrayList<DetalleVentaDTO>( list.size() );
        for ( DetalleVenta detalleVenta : list ) {
            list1.add( detalleVentaToDetalleVentaDTO( detalleVenta ) );
        }

        return list1;
    }
}
