package com.futebolcamisas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Carrinho() {}

    // Getter e setter para usuário
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        Optional<ItemCarrinho> itemExistente = itens.stream()
                .filter(item -> item.getProduto().getId().equals(produto.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            itemExistente.get().setQuantidade(
                    itemExistente.get().getQuantidade() + quantidade
            );
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);
            novoItem.setCarrinho(this);
            itens.add(novoItem);
        }
    }

    public void removerProduto(Long produtoId) {
        itens.removeIf(item -> item.getProduto().getId().equals(produtoId));
    }

    public void atualizarQuantidade(Long produtoId, int novaQuantidade) {
        itens.stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst()
                .ifPresent(item -> item.setQuantidade(novaQuantidade));
    }

    public void limpar() {
        itens.clear();
    }

    public double getTotal() {
        return itens.stream()
                .mapToDouble(ItemCarrinho::getSubtotal)
                .sum();
    }

    public int getQuantidadeTotal() {
        return itens.stream()
                .mapToInt(ItemCarrinho::getQuantidade)
                .sum();
    }

    public boolean isEmpty() {
        return itens.isEmpty();
    }
}
