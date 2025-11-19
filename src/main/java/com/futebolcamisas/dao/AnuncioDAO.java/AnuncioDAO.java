package com.futebolcamisas.dao;

import com.futebolcamisas.model.Anuncio;
import java.util.ArrayList;
import java.util.List;

public class AnuncioDAO {
    private static List<Anuncio> anuncios = new ArrayList<>();
    private static Long proximoId = 1L;

    // Salvar novo anúncio
    public void salvar(Anuncio anuncio) {
        if (anuncio.getId() == null) {
            anuncio.setId(proximoId++);
        }
        anuncios.add(anuncio);
    }

    // Listar todos os anúncios
    public List<Anuncio> listarTodos() {
        return new ArrayList<>(anuncios);
    }

    // Obter anúncio por ID
    public Anuncio obterPorId(Long id) {
        return anuncios.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Atualizar anúncio
    public void atualizar(Anuncio anuncio) {
        Anuncio existente = obterPorId(anuncio.getId());
        if (existente != null) {
            int index = anuncios.indexOf(existente);
            anuncios.set(index, anuncio);
        }
    }

    // Deletar anúncio
    public void deletar(Long id) {
        anuncios.removeIf(a -> a.getId().equals(id));
    }

    // Verificar se existe anúncio
    public boolean existe(Long id) {
        return obterPorId(id) != null;
    }

    // Contar total de anúncios
    public long contarTotal() {
        return anuncios.size();
    }
}
