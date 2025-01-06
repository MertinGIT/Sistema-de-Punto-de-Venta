package com.grupo1.pos.controller;

public class LoginResponseDTO {

        private String token;

        // Constructor
        public LoginResponseDTO(String token) {
            this.token = token;
        }

        // Getter y Setter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
