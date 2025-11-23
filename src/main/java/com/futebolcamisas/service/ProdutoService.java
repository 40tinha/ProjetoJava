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

    // LISTAR TODOS (usado na listagem pública e admin)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // BUSCAR POR ID (usado em detalhes, editar, deletar)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // SALVAR / ATUALIZAR (usado em salvar e atualizar do admin)
    public Produto salvar(Produto produto) {
        // validações simples (pode melhorar depois)
        if (produto.getTitulo() == null || produto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("Título é obrigatório");
        }
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new RuntimeException("Preço deve ser maior que zero");
        }
        if (produto.getEstoque() == null) {
            produto.setEstoque(0);
        }
        if (produto.getEstoque() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo");
        }

        return produtoRepository.save(produto);
    }

    // DELETAR (usado no adminDeletar)
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

    // BUSCA COM FILTROS (se ainda estiver usando em algum lugar)
    public List<Produto> buscarComFiltros(String time, String marca, String ordenacao) {
        List<Produto> produtos;

        // Busca com filtro, caso passado
        if ((time != null && !time.isEmpty()) || (marca != null && !marca.isEmpty())) {
            produtos = produtoRepository
                    .findByTimeContainingIgnoreCaseAndMarcaContainingIgnoreCase(
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
                case "preco":
                    produtos.sort(Comparator.comparing(Produto::getPreco));
                    break;
                case "preco-maior":
                case "preco-desc":
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
