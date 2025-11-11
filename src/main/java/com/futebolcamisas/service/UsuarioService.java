package com.futebolcamisas.service;

import com.futebolcamisas.dao.UsuarioDAO;
import com.futebolcamisas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDAO usuarioDAO;

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioDAO.existeEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }
        return usuarioDAO.salvar(usuario);
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Email ou senha inválidos");
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }
}

