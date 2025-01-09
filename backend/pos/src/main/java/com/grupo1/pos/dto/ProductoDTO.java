package com.grupo1.pos.dto;

import jakarta.validation.constraints.*;

public class ProductoDTO {

    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    private String nombre;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Long stock;

    private String codigoBarras;

    @NotNull
    @Size(max = 50, message = "La categoría debe tener máximo 50 caracteres")
    private String categoria;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
