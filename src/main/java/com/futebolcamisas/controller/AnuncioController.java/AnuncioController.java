package com.futebolcamisas.controller;

import com.futebolcamisas.model.Anuncio;
import com.futebolcamisas.dao.AnuncioDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/anuncios")
public class AnuncioController {
    
    private static AnuncioDAO dao = new AnuncioDAO();
    
    // Listar todos os anúncios
    @GetMapping
    public String listar(Model model) {
        List<Anuncio> anuncios = dao.listarTodos();
        model.addAttribute("anuncios", anuncios);
        return "admin/anuncios-list";
    }
    
    // Página para criar novo anúncio
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("anuncio", new Anuncio());
        return "admin/anuncios-form";
    }
    
    // Salvar novo anúncio
    @PostMapping
    public String salvar(@ModelAttribute Anuncio anuncio) {
        dao.salvar(anuncio);
        return "redirect:/admin/anuncios";
    }
    
    // Página para editar anúncio
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Anuncio anuncio = dao.obterPorId(id);
        if (anuncio == null) {
            return "redirect:/admin/anuncios";
        }
        model.addAttribute("anuncio", anuncio);
        return "admin/anuncios-form";
    }
    
    // Atualizar anúncio
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Anuncio anuncio) {
        anuncio.setId(id);
        dao.atualizar(anuncio);
        return "redirect:/admin/anuncios";
    }
    
    // Deletar anúncio
    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        dao.deletar(id);
        return "redirect:/admin/anuncios";
    }
}
