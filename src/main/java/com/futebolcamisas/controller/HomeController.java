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
import org.springframework.web.bind.annotation.RequestParam;
import com.futebolcamisas.model.Anuncio;
import com.futebolcamisas.repository.AnuncioRepository;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String time,
                        @RequestParam(required = false) String marca,
                        @RequestParam(required = false, defaultValue = "relevancia") String sort,
                        HttpSession session,
                        Model model) {
        // Exemplo: Buscar todos os anúncios (produtos)
        List<Anuncio> anuncios = anuncioRepository.findAll();
        model.addAttribute("produtos", anuncios); // Ou "anuncios", conforme template
        model.addAttribute("timeSelecionado", time);
        model.addAttribute("marcaSelecionada", marca);
        model.addAttribute("sortSelecionado", sort);

        Carrinho carrinho = carrinhoService.obterCarrinho(session);
        session.setAttribute("carrinho", carrinho);
        return "index";
    }
}
