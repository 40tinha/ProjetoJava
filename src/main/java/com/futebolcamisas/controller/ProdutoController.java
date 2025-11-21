package com.futebolcamisas.controller;

import com.futebolcamisas.model.Carrinho;
import com.futebolcamisas.model.Anuncio;
import com.futebolcamisas.repository.AnuncioRepository;
import com.futebolcamisas.service.CarrinhoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProdutoController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/produto/{id}")
    public String detalhesProduto(@PathVariable Long id, HttpSession session, Model model) {
        try {
            Anuncio anuncio = anuncioRepository.findById(id).orElse(null);
            if (anuncio == null) { // Corrigido!
                return "redirect:/";
            }
            model.addAttribute("anuncio", anuncio); // No HTML, use ${anuncio.campo}

            Carrinho carrinho = carrinhoService.obterCarrinho(session);
            session.setAttribute("carrinho", carrinho);

            return "produto";
        } catch (RuntimeException e) {
            return "redirect:/";
        }
    }
}

