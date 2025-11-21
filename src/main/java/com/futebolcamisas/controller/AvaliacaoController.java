package com.futebolcamisas.controller;

import com.futebolcamisas.model.Avaliacao;
import com.futebolcamisas.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/anuncios/{anuncioId}/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @GetMapping("/{avaliacaoId}/editar")
    public String editarAvaliacao(@PathVariable Long anuncioId, @PathVariable Long avaliacaoId, Model model) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId).orElse(null);
        model.addAttribute("avaliacao", avaliacao);
        model.addAttribute("anuncioId", anuncioId);
        return "admin/avaliacao-form";
    }

    @PostMapping("/{avaliacaoId}")
    public String atualizarAvaliacao(@PathVariable Long anuncioId, @PathVariable Long avaliacaoId,
                                     @ModelAttribute Avaliacao avaliacao) {
        avaliacao.setId(avaliacaoId);
        avaliacaoRepository.save(avaliacao);
        return "redirect:/admin/anuncios/" + anuncioId + "/detalhar";
    }

    @GetMapping("/{avaliacaoId}/deletar")
    public String deletarAvaliacao(@PathVariable Long anuncioId, @PathVariable Long avaliacaoId) {
        avaliacaoRepository.deleteById(avaliacaoId);
        return "redirect:/admin/anuncios/" + anuncioId + "/detalhar";
    }
}
