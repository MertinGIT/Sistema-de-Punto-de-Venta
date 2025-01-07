package com.grupo1.pos.controller;

public class LoginRequestDTO {

    private String email;
    private String password;

    // Constructor vacío (necesario para deserialización de JSON)
    public LoginRequestDTO() {
    }

    // Constructor con parámetros
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y setters
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
