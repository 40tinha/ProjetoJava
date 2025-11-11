package com.futebolcamisas.controller;

import com.futebolcamisas.model.Produto;
import com.futebolcamisas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produto/{id}")
    public String detalhesProduto(@PathVariable Long id, Model model) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            model.addAttribute("produto", produto);
            return "produto";
        } catch (RuntimeException e) {
            return "redirect:/";
        }
    }
}

