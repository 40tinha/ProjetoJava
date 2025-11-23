package com.futebolcamisas.controller;

import com.futebolcamisas.model.Carrinho;
import com.futebolcamisas.model.Produto;
import com.futebolcamisas.service.CarrinhoService;
import com.futebolcamisas.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    private static final Logger logger = LoggerFactory.getLogger(CarrinhoController.class);

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;  // ✅ Service (não DAO)

    @GetMapping
    public String verCarrinho(HttpSession session, Model model) {
        logger.info("📋 Acessando carrinho");
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

        logger.info("🛒 Tentando adicionar produto {} ao carrinho", id);

        try {
            Produto produto = produtoService.buscarPorId(id);  // ✅ Service

            carrinhoService.adicionarAoCarrinho(session, produto, quantidade);
            logger.info("✅ Produto {} adicionado ao carrinho", id);
            redirectAttributes.addFlashAttribute("mensagem", "✅ Produto adicionado ao carrinho!");

        } catch (RuntimeException e) {
            logger.warn("❌ Produto não encontrado: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("erro", "❌ Produto não encontrado!");
        } catch (Exception e) {
            logger.error("❌ Erro ao adicionar ao carrinho: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("erro", "❌ Erro ao adicionar ao carrinho!");
        }

        return "redirect:/";
    }

    @PostMapping("/remover/{id}")
    public String removerDoCarrinho(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        logger.info("🗑️ Removendo produto {} do carrinho", id);
        carrinhoService.removerDoCarrinho(session, id);
        redirectAttributes.addFlashAttribute("mensagem", "🗑️ Produto removido do carrinho!");
        return "redirect:/carrinho";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam int quantidade,
            HttpSession session) {

        logger.info("📊 Atualizando quantidade do produto {} para {}", id, quantidade);
        carrinhoService.atualizarQuantidade(session, id, quantidade);
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String limparCarrinho(HttpSession session, RedirectAttributes redirectAttributes) {
        logger.info("🧹 Limpando carrinho");
        carrinhoService.limparCarrinho(session);
        redirectAttributes.addFlashAttribute("mensagem", "✅ Carrinho limpo com sucesso!");
        return "redirect:/carrinho";
    }

    @PostMapping("/finalizar")
    public String finalizarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        logger.info("💳 Finalizando compra");

        Carrinho carrinho = carrinhoService.obterCarrinho(session);

        if (carrinho.isEmpty()) {
            logger.warn("❌ Tentativa de finalizar com carrinho vazio");
            redirectAttributes.addFlashAttribute("erro", "❌ Carrinho vazio!");
            return "redirect:/carrinho";
        }

        if (session.getAttribute("usuarioLogado") == null) {
            logger.warn("❌ Tentativa de finalizar sem login");
            redirectAttributes.addFlashAttribute("erro", "❌ Faça login para finalizar a compra!");
            return "redirect:/login";
        }

        logger.info("✅ Compra finalizada com sucesso");
        carrinhoService.limparCarrinho(session);
        redirectAttributes.addFlashAttribute("mensagem", "✅ Compra finalizada com sucesso!");
        return "redirect:/";
    }
}
