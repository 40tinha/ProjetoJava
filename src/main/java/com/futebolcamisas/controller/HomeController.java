package com.futebolcamisas.controller;

import com.futebolcamisas.model.Produto;
import com.futebolcamisas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String time,
                       @RequestParam(required = false) String marca,
                       @RequestParam(required = false, defaultValue = "relevancia") String sort,
                       Model model) {
        List<Produto> produtos = produtoService.buscarComFiltros(time, marca, sort);
        model.addAttribute("produtos", produtos);
        model.addAttribute("timeSelecionado", time);
        model.addAttribute("marcaSelecionada", marca);
        model.addAttribute("sortSelecionado", sort);
        return "index";
    }
}

