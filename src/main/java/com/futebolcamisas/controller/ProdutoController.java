package com.futebolcamisas.controller;

import com.futebolcamisas.model.Carrinho;
import com.futebolcamisas.model.Produto;
import com.futebolcamisas.model.Usuario;
import com.futebolcamisas.model.Avaliacao;
import com.futebolcamisas.service.CarrinhoService;
import com.futebolcamisas.service.ProdutoService;
import com.futebolcamisas.service.AvaliacaoService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private AvaliacaoService avaliacaoService;

    // ================== PÚBLICO ==================

    @GetMapping
    public String listarPublico(@RequestParam(required = false) String time,
                                @RequestParam(required = false) String marca,
                                @RequestParam(required = false) String temporada,
                                @RequestParam(required = false, defaultValue = "relevancia") String sort,
                                HttpSession session,
                                Model model) {

        List<Produto> produtos = produtoService.listarTodos();

        // Normalizar para comparação case-insensitive
        String timeFiltro = (time == null) ? null : time.trim().toLowerCase();
        String marcaFiltro = (marca == null) ? null : marca.trim().toLowerCase();
        String tempFiltro = (temporada == null) ? null : temporada.trim().toLowerCase();

        if (timeFiltro != null && !timeFiltro.isBlank()) {
            produtos = produtos.stream()
                    .filter(p -> p.getTime() != null &&
                            p.getTime().toLowerCase().contains(timeFiltro))
                    .toList();
        }

        if (marcaFiltro != null && !marcaFiltro.isBlank()) {
            produtos = produtos.stream()
                    .filter(p -> p.getMarca() != null &&
                            p.getMarca().toLowerCase().contains(marcaFiltro))
                    .toList();
        }

        if (tempFiltro != null && !tempFiltro.isBlank()) {
            produtos = produtos.stream()
                    .filter(p -> p.getTemporada() != null &&
                            p.getTemporada().toLowerCase().contains(tempFiltro))
                    .toList();
        }

        // Ordenação
        if ("preco".equalsIgnoreCase(sort)) {
            produtos = produtos.stream()
                    .sorted((p1, p2) -> p1.getPreco().compareTo(p2.getPreco()))
                    .toList();
        } else if ("preco-desc".equalsIgnoreCase(sort)) {
            produtos = produtos.stream()
                    .sorted((p1, p2) -> p2.getPreco().compareTo(p1.getPreco()))
                    .toList();
        }

        Carrinho carrinho = carrinhoService.obterCarrinho(session);

        model.addAttribute("produtos", produtos);
        model.addAttribute("carrinho", carrinho);

        // devolver valores para manter nos inputs
        model.addAttribute("timeSelecionado", time);
        model.addAttribute("marcaSelecionada", marca);
        model.addAttribute("temporadaSelecionada", temporada);
        model.addAttribute("sortSelecionado", sort);

        return "index";
    }



    // Detalhe com avaliações
    @GetMapping("/detalhe/{id}")
    public String detalhe(@PathVariable Long id,
                          HttpSession session,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
            return "redirect:/produtos";
        }

        Carrinho carrinho = carrinhoService.obterCarrinho(session);
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorProduto(id);
        Double mediaNotas = avaliacaoService.calcularMediaNotasProduto(id);

        model.addAttribute("produto", produto);
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("mediaNotas", mediaNotas);
        model.addAttribute("totalAvaliacoes", avaliacoes.size());

        return "produto-detalhe";
    }

    // Enviar avaliação
    @PostMapping("/{id}/avaliacoes")
    public String adicionarAvaliacao(@PathVariable Long id,
                                     @RequestParam String autor,
                                     @RequestParam String texto,
                                     @RequestParam Integer estrelas,
                                     RedirectAttributes redirectAttributes) {

        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
            return "redirect:/produtos";
        }

        Avaliacao avaliacao = new Avaliacao(autor, texto, estrelas, produto);
        avaliacaoService.criarAvaliacao(avaliacao);

        redirectAttributes.addFlashAttribute("sucesso", "Avaliação enviada com sucesso!");
        return "redirect:/produtos/detalhe/" + id;
    }

    // ================== ADMIN ==================

    private boolean isAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return usuario != null && "ADMIN".equals(usuario.getRole());
    }

    // Lista admin: /produtos/admin
    @GetMapping("/admin")
    public String adminListar(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        List<Produto> produtos = produtoService.listarTodos();
        model.addAttribute("produtos", produtos);
        return "admin/anuncios-list";
    }

    // Form novo: /produtos/admin/novo
    @GetMapping("/admin/novo")
    public String adminNovo(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("produto", new Produto());
        return "admin/anuncios-form";
    }

    // Salvar novo: POST /produtos/admin
    @PostMapping("/admin")
    public String adminSalvar(HttpSession session,
                              @ModelAttribute Produto produto,
                              RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        if (produto.getTitulo() == null || produto.getTitulo().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Título é obrigatório!");
            return "redirect:/produtos/admin/novo";
        }

        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("sucesso", "Produto criado com sucesso!");
        return "redirect:/produtos/admin";
    }

    // Form editar: /produtos/admin/{id}/editar
    @GetMapping("/admin/{id}/editar")
    public String adminEditar(@PathVariable Long id,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
            return "redirect:/produtos/admin";
        }

        model.addAttribute("produto", produto);
        return "admin/anuncios-form";
    }

    // Atualizar: POST /produtos/admin/{id}
    @PostMapping("/admin/{id}")
    public String adminAtualizar(@PathVariable Long id,
                                 HttpSession session,
                                 @ModelAttribute Produto produto,
                                 RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Produto existente = produtoService.buscarPorId(id);
        if (existente == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
            return "redirect:/produtos/admin";
        }

        produto.setId(id);
        produtoService.salvar(produto);

        redirectAttributes.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        return "redirect:/produtos/admin";
    }

    // Deletar: POST /produtos/admin/{id}/deletar
    @PostMapping("/admin/{id}/deletar")
    public String adminDeletar(@PathVariable Long id,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/login";
        }

        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado!");
            return "redirect:/produtos/admin";
        }

        produtoService.deletar(id);
        redirectAttributes.addFlashAttribute("sucesso", "Produto deletado com sucesso!");
        return "redirect:/produtos/admin";
    }
}
