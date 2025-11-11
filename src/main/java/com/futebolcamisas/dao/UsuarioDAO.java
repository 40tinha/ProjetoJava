package com.futebolcamisas.dao;

import com.futebolcamisas.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UsuarioDAO {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    static {
        // Dados de exemplo para teste
        usuarios.add(new Usuario("Admin", "admin@futebolcamisas.com", "admin123"));
        usuarios.get(0).setId(counter.incrementAndGet());
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(counter.incrementAndGet());
            usuarios.add(usuario);
        } else {
            int index = usuarios.indexOf(usuario);
            if (index >= 0) {
                usuarios.set(index, usuario);
            }
        }
        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorId(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public boolean existeEmail(String email) {
        return usuarios.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}

