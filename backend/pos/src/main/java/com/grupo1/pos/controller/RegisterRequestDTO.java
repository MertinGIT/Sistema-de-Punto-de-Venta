package com.grupo1.pos.controller;

public class RegisterRequestDTO {
    private String nombre;
    private String email;
    private String password;
    private String rol;

    // Getters y setters
    public String getNombre() {
        return nombre;
    }
    public String getRol() {
        return rol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
