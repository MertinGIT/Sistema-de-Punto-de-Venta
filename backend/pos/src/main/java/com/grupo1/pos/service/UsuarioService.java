package com.grupo1.pos.service;

import com.grupo1.pos.model.Usuario;
import com.grupo1.pos.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    List<Usuario> getUsuarios();

    void registrarUsuario(UsuarioDTO usuarioDTO);

    void actualizarUsuario(Long id, UsuarioDTO usuarioDTO);

    void eliminarUsuario(Long id);

    boolean isAdmin(String token);
}
