package com.grupo1.pos.util;

import com.grupo1.pos.dto.ProductoDTO;
import com.grupo1.pos.model.Producto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-09T13:40:38-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDTO toDTO(Producto producto) {
        if ( producto == null ) {
            return null;
        }

        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setId( producto.getId() );
        productoDTO.setNombre( producto.getNombre() );
        productoDTO.setPrecio( producto.getPrecio() );
        productoDTO.setStock( producto.getStock() );
        productoDTO.setCodigoBarras( producto.getCodigoBarras() );
        productoDTO.setCategoria( producto.getCategoria() );

        return productoDTO;
    }

    @Override
    public Producto toEntity(ProductoDTO productoDTO) {
        if ( productoDTO == null ) {
            return null;
        }

        Producto producto = new Producto();

        producto.setCategoria( productoDTO.getCategoria() );
        producto.setCodigoBarras( productoDTO.getCodigoBarras() );
        producto.setStock( productoDTO.getStock() );
        producto.setPrecio( productoDTO.getPrecio() );
        producto.setNombre( productoDTO.getNombre() );
        producto.setId( productoDTO.getId() );

        return producto;
    }
}
