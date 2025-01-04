package com.grupo1.pos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    @Column(length = 100, nullable = false)
    private String nombre;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Long stock;

    @Pattern(regexp = "^[0-9]{13}$", message = "El código de barras debe ser un número de 13 dígitos")
    @Column(unique = true)
    private String codigoBarras;

    @NotNull
    @Size(max = 50, message = "La categoría debe tener máximo 50 caracteres")
    @Column(length = 50, nullable = false)
    private String categoria;

    public Producto() {}

    public Producto(String nombre, Double precio, Long stock, String codigoBarras, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
