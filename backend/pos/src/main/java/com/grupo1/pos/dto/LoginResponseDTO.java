package com.grupo1.pos.dto;

public class LoginResponseDTO {

        private String token;
        private Long idUsuario;

        // Constructor
        public LoginResponseDTO(String token, Long idUsuario) {
            this.token = token;
            this.idUsuario = idUsuario;
        }

        // Getter y Setter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getIdUsuario() {
            return idUsuario;
        }
        public void setIdUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
        }
    }
