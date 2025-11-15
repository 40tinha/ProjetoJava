package com.futebolcamisas.controller;

import com.futebolcamisas.model.Carrinho;
import com.futebolcamisas.model.Produto;
import com.futebolcamisas.service.CarrinhoService;
import com.futebolcamisas.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/produto/{id}")
    public String detalhesProduto(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            model.addAttribute("produto", produto);
            
            // Adiciona o carrinho à sessão para exibir contador
            Carrinho carrinho = carrinhoService.obterCarrinho(session);
            session.setAttribute("carrinho", carrinho);
            
            return "produto";
        } catch (RuntimeException e) {
            return "redirect:/";
        }
    }
}
