package com.futebolcamisas.service;

import com.futebolcamisas.model.Produto;
import com.futebolcamisas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public List<Produto> buscarComFiltros(String time, String marca, String ordenacao) {
        List<Produto> produtos;

        // Busca com filtro, caso passado
        if ((time != null && !time.isEmpty()) || (marca != null && !marca.isEmpty())) {
            produtos = produtoRepository.findByTimeContainingIgnoreCaseAndMarcaContainingIgnoreCase(
                    time == null ? "" : time,
                    marca == null ? "" : marca
            );
        } else {
            produtos = produtoRepository.findAll();
        }

        // Ordenação (na memória)
        if (ordenacao != null && !ordenacao.isEmpty()) {
            switch (ordenacao) {
                case "preco-menor":
                    produtos.sort(Comparator.comparing(Produto::getPreco));
                    break;
                case "preco-maior":
                    produtos.sort(Comparator.comparing(Produto::getPreco).reversed());
                    break;
                default:
                    // Relevância/padrão
                    break;
            }
        }

        return produtos;
    }
}
