package com.futebolcamisas.service;

import com.futebolcamisas.model.Carrinho;
import com.futebolcamisas.model.Produto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {
    private static final String CARRINHO_SESSION_KEY = "carrinho";

    public Carrinho obterCarrinho(HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute(CARRINHO_SESSION_KEY);
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
        }
        return carrinho;
    }

    public void adicionarAoCarrinho(HttpSession session, Produto produto, int quantidade) {
        Carrinho carrinho = obterCarrinho(session);
        carrinho.adicionarProduto(produto, quantidade);
        session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
    }

    public void removerDoCarrinho(HttpSession session, Long produtoId) {
        Carrinho carrinho = obterCarrinho(session);
        carrinho.removerProduto(produtoId);
        session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
    }

    public void atualizarQuantidade(HttpSession session, Long produtoId, int quantidade) {
        Carrinho carrinho = obterCarrinho(session);
        if (quantidade <= 0) {
            carrinho.removerProduto(produtoId);
        } else {
            carrinho.atualizarQuantidade(produtoId, quantidade);
        }
        session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
    }

    public void limparCarrinho(HttpSession session) {
        Carrinho carrinho = obterCarrinho(session);
        carrinho.limpar();
        session.setAttribute(CARRINHO_SESSION_KEY, carrinho);
    }
}
