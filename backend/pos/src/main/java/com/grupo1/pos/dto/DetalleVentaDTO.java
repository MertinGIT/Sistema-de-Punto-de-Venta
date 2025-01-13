package com.grupo1.pos.dto;

public class DetalleVentaDTO {

    private Long id;
    private Long ventaId;
    private Long producto_id;
    private Integer cantidad;
    private Double subtotal;
    private Double iva10;
    private Double iva5;
    // Getters y setters
    public Long getId() {
        return id;
    }

    public DetalleVentaDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Long getProductoId() {
        return producto_id;
    }

    public void setProductoId(Long producto_id) {
        this.producto_id = producto_id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public DetalleVentaDTO( Long id,Integer cantidad, Double subtotal,Long producto_id, Long ventaId) {
        this.cantidad = cantidad;
        this.id = id;
        this.subtotal = subtotal;
        this.producto_id = producto_id;
        this.ventaId = ventaId;
    }

    public Double getIva10() {
        return iva10;
    }

    public void setIva10(Double iva10) {
        this.iva10 = iva10;
    }

    public Double getIva5() {
        return iva5;
    }

    public void setIva5(Double iva5) {
        this.iva5 = iva5;
    }
}
