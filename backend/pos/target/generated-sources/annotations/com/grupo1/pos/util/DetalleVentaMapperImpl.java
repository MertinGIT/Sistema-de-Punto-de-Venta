package com.grupo1.pos.util;

import com.grupo1.pos.dto.DetalleVentaDTO;
import com.grupo1.pos.model.DetalleVenta;
import com.grupo1.pos.model.Producto;
import com.grupo1.pos.model.Venta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T01:49:07-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class DetalleVentaMapperImpl implements DetalleVentaMapper {

    @Override
    public DetalleVenta toEntity(DetalleVentaDTO detalleVentaDTO) {
        if ( detalleVentaDTO == null ) {
            return null;
        }

        DetalleVenta detalleVenta = new DetalleVenta();

        detalleVenta.setVenta( detalleVentaDTOToVenta( detalleVentaDTO ) );
        detalleVenta.setProducto( detalleVentaDTOToProducto( detalleVentaDTO ) );
        detalleVenta.setId( detalleVentaDTO.getId() );
        detalleVenta.setCantidad( detalleVentaDTO.getCantidad() );
        detalleVenta.setSubtotal( detalleVentaDTO.getSubtotal() );

        return detalleVenta;
    }

    @Override
    public DetalleVentaDTO toDTO(DetalleVenta detalleVenta) {
        if ( detalleVenta == null ) {
            return null;
        }

        DetalleVentaDTO detalleVentaDTO = new DetalleVentaDTO();

        detalleVentaDTO.setVentaId( detalleVentaVentaId( detalleVenta ) );
        detalleVentaDTO.setProductoId( detalleVentaProductoId( detalleVenta ) );
        detalleVentaDTO.setId( detalleVenta.getId() );
        detalleVentaDTO.setCantidad( detalleVenta.getCantidad() );
        detalleVentaDTO.setSubtotal( detalleVenta.getSubtotal() );
        detalleVentaDTO.setIva10( detalleVenta.getIva10() );
        detalleVentaDTO.setIva5( detalleVenta.getIva5() );

        return detalleVentaDTO;
    }

    protected Venta detalleVentaDTOToVenta(DetalleVentaDTO detalleVentaDTO) {
        if ( detalleVentaDTO == null ) {
            return null;
        }

        Venta venta = new Venta();

        venta.setId( detalleVentaDTO.getVentaId() );

        return venta;
    }

    protected Producto detalleVentaDTOToProducto(DetalleVentaDTO detalleVentaDTO) {
        if ( detalleVentaDTO == null ) {
            return null;
        }

        Producto producto = new Producto();

        producto.setId( detalleVentaDTO.getProductoId() );

        return producto;
    }

    private Long detalleVentaVentaId(DetalleVenta detalleVenta) {
        if ( detalleVenta == null ) {
            return null;
        }
        Venta venta = detalleVenta.getVenta();
        if ( venta == null ) {
            return null;
        }
        Long id = venta.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long detalleVentaProductoId(DetalleVenta detalleVenta) {
        if ( detalleVenta == null ) {
            return null;
        }
        Producto producto = detalleVenta.getProducto();
        if ( producto == null ) {
            return null;
        }
        Long id = producto.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
