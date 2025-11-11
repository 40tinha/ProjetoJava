package com.futebolcamisas.service;

import com.futebolcamisas.dao.ProdutoDAO;
import com.futebolcamisas.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoDAO produtoDAO;

    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        Produto produto = produtoDAO.buscarPorId(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }
        return produto;
    }

    public List<Produto> buscarComFiltros(String time, String marca, String ordenacao) {
        List<Produto> produtos;
        
        if (time != null && !time.isEmpty() || marca != null && !marca.isEmpty()) {
            produtos = produtoDAO.buscarPorFiltros(time, marca);
        } else {
            produtos = produtoDAO.listarTodos();
        }

        // Aplicar ordenação
        if (ordenacao != null && !ordenacao.isEmpty()) {
            switch (ordenacao) {
                case "preco-menor":
                    produtos.sort(Comparator.comparing(Produto::getPreco));
                    break;
                case "preco-maior":
                    produtos.sort(Comparator.comparing(Produto::getPreco).reversed());
                    break;
                default:
                    // Relevância (mantém ordem original)
                    break;
            }
        }

        return produtos;
    }
}

