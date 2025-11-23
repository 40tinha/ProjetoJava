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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String time,
                        @RequestParam(required = false) String marca,
                        @RequestParam(required = false, defaultValue = "relevancia") String sort,
                        HttpSession session,
                        Model model) {

        logger.info("📄 Carregando home com filtros - Time: {}, Marca: {}, Sort: {}", time, marca, sort);

        try {
            // Buscar todos os produtos
            List<Produto> produtos = produtoService.listarTodos();

            // Filtrar por time se especificado
            if (time != null && !time.isEmpty()) {
                logger.info("🔍 Filtrando por time: {}", time);
                produtos = produtos.stream()
                        .filter(p -> p.getTime().equalsIgnoreCase(time))
                        .toList();
            }

            // Filtrar por marca se especificado
            if (marca != null && !marca.isEmpty()) {
                logger.info("🔍 Filtrando por marca: {}", marca);
                produtos = produtos.stream()
                        .filter(p -> p.getMarca().equalsIgnoreCase(marca))
                        .toList();
            }

            // Ordenar conforme especificado
            if ("preco".equalsIgnoreCase(sort)) {
                logger.info("📊 Ordenando por preço");
                produtos = produtos.stream()
                        .sorted((p1, p2) -> p1.getPreco().compareTo(p2.getPreco()))
                        .toList();
            } else if ("preco-desc".equalsIgnoreCase(sort)) {
                logger.info("📊 Ordenando por preço decrescente");
                produtos = produtos.stream()
                        .sorted((p1, p2) -> p2.getPreco().compareTo(p1.getPreco()))
                        .toList();
            }

            // Adicionar ao model
            model.addAttribute("produtos", produtos);
            model.addAttribute("timeSelecionado", time);
            model.addAttribute("marcaSelecionada", marca);
            model.addAttribute("sortSelecionado", sort);

            // Obter carrinho
            Carrinho carrinho = carrinhoService.obterCarrinho(session);
            model.addAttribute("carrinho", carrinho);

            logger.info("✅ Home carregada com {} produtos", produtos.size());
            return "index";

        } catch (Exception e) {
            logger.error("❌ Erro ao carregar home: {}", e.getMessage(), e);
            return "error";
        }
    }
}
