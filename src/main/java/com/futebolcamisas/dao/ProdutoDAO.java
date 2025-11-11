package com.futebolcamisas.dao;

import com.futebolcamisas.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProdutoDAO {
    private static final List<Produto> produtos = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    static {
        // Dados de exemplo
        produtos.add(criarProduto("Camisa Corinthians I 2024/25", 
            "Camisa oficial do Corinthians temporada 2024/25", 
            299.99, 
            "https://via.placeholder.com/300x300?text=Corinthians",
            "Corinthians", "Nike", "2024/25"));
        
        produtos.add(criarProduto("Camisa Flamengo I 2024/25",
            "Camisa oficial do Flamengo temporada 2024/25",
            349.99,
            "https://via.placeholder.com/300x300?text=Flamengo",
            "Flamengo", "Adidas", "2024/25"));
        
        produtos.add(criarProduto("Camisa Palmeiras I 2024/25",
            "Camisa oficial do Palmeiras temporada 2024/25",
            319.99,
            "https://via.placeholder.com/300x300?text=Palmeiras",
            "Palmeiras", "Puma", "2024/25"));
        
        produtos.add(criarProduto("Camisa São Paulo I 2024/25",
            "Camisa oficial do São Paulo temporada 2024/25",
            329.99,
            "https://via.placeholder.com/300x300?text=Sao+Paulo",
            "São Paulo", "Adidas", "2024/25"));
    }

    private static Produto criarProduto(String nome, String descricao, Double preco, 
                                       String urlImagem, String time, String marca, String temporada) {
        Produto produto = new Produto(nome, descricao, preco, urlImagem, time, marca, temporada);
        produto.setId(counter.incrementAndGet());
        produto.setEstoque(50);
        return produto;
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    public Produto buscarPorId(Long id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Produto> buscarPorTime(String time) {
        return produtos.stream()
                .filter(p -> p.getTime().equalsIgnoreCase(time))
                .collect(Collectors.toList());
    }

    public List<Produto> buscarPorMarca(String marca) {
        return produtos.stream()
                .filter(p -> p.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    public List<Produto> buscarPorFiltros(String time, String marca) {
        return produtos.stream()
                .filter(p -> (time == null || p.getTime().equalsIgnoreCase(time))
                          && (marca == null || p.getMarca().equalsIgnoreCase(marca)))
                .collect(Collectors.toList());
    }

    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(counter.incrementAndGet());
            produtos.add(produto);
        } else {
            int index = produtos.indexOf(produto);
            if (index >= 0) {
                produtos.set(index, produto);
            }
        }
        return produto;
    }
}

