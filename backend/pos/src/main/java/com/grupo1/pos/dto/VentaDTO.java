package com.grupo1.pos.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VentaDTO {

    private Long id;
    private Date fecha;
    private Long usuarioId;
    private Double montoTotal;
    private Long producto_id;

    public void setProductoId(Long producto_id) {
        this.producto_id = producto_id;
    }

    public Long getProductoId() {
        return producto_id;
    }

    private Long productoId;
    private String metodoPago;
    private List<Long> detallesIds;
    private List<DetalleVentaDTO> detalles= new ArrayList<>();

    public List<DetalleVentaDTO> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<Long> getDetallesIds() {
        return detallesIds;
    }

    public void setDetallesIds(List<Long> detallesIds) {
        this.detallesIds = detallesIds;
    }
}
