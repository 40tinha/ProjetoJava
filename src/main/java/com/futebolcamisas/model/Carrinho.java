package com.futebolcamisas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrinho {
    private List<ItemCarrinho> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
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
            itens.add(new ItemCarrinho(produto, quantidade));
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
