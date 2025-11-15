package com.futebolcamisas.controller;

import com.futebolcamisas.model.Carrinho;
import com.futebolcamisas.model.Produto;
import com.futebolcamisas.service.CarrinhoService;
import com.futebolcamisas.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String verCarrinho(HttpSession session, Model model) {
        Carrinho carrinho = carrinhoService.obterCarrinho(session);
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
        return "carrinho";
    }

    @PostMapping("/adicionar/{id}")
    public String adicionarAoCarrinho(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int quantidade,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Produto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            carrinhoService.adicionarAoCarrinho(session, produto, quantidade);
            redirectAttributes.addFlashAttribute("mensagem", "Produto adicionado ao carrinho!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
        }
        return "redirect:/";
    }

    @PostMapping("/remover/{id}")
    public String removerDoCarrinho(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        carrinhoService.removerDoCarrinho(session, id);
        redirectAttributes.addFlashAttribute("mensagem", "Produto removido do carrinho!");
        return "redirect:/carrinho";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam int quantidade,
            HttpSession session) {
        
        carrinhoService.atualizarQuantidade(session, id, quantidade);
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String limparCarrinho(HttpSession session, RedirectAttributes redirectAttributes) {
        carrinhoService.limparCarrinho(session);
        redirectAttributes.addFlashAttribute("mensagem", "Carrinho limpo com sucesso!");
        return "redirect:/carrinho";
    }

    @PostMapping("/finalizar")
    public String finalizarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        Carrinho carrinho = carrinhoService.obterCarrinho(session);
        
        if (carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Carrinho vazio!");
            return "redirect:/carrinho";
        }
        
        if (session.getAttribute("usuarioLogado") == null) {
            redirectAttributes.addFlashAttribute("erro", "Faça login para finalizar a compra!");
            return "redirect:/login";
        }
        
        // Aqui você pode adicionar lógica de pagamento e criação de pedido
        carrinhoService.limparCarrinho(session);
        redirectAttributes.addFlashAttribute("mensagem", "Compra finalizada com sucesso!");
        return "redirect:/";
    }
}
