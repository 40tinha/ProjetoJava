package com.futebolcamisas.controller.AnuncioController;

import com.futebolcamisas.model.Anuncio;
import com.futebolcamisas.model.Usuario;
import com.futebolcamisas.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @GetMapping
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        List<Anuncio> anuncios = anuncioRepository.findAll();
        model.addAttribute("anuncios", anuncios);
        return "admin/anuncios-list";
    }

    @GetMapping("/novo")
    public String novo(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("anuncio", new Anuncio());
        return "admin/anuncios-form";
    }

    @PostMapping
    public String salvar(HttpSession session, @ModelAttribute Anuncio anuncio) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        Anuncio salvo = anuncioRepository.save(anuncio);
        return "redirect:/produto/" + salvo.getId(); // Redireciona para visão do produto
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        Anuncio anuncio = anuncioRepository.findById(id).orElse(null);
        if (anuncio == null) {
            return "redirect:/admin/anuncios";
        }
        model.addAttribute("anuncio", anuncio);
        return "admin/anuncios-form";
    }

    @GetMapping("/{id}/detalhar")
    public String detalhar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        Anuncio anuncio = anuncioRepository.findById(id).orElse(null);
        if (anuncio == null) {
            return "redirect:/admin/anuncios";
        }
        model.addAttribute("anuncio", anuncio);
        return "admin/anuncios-detalhe";
    }


    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, HttpSession session, @ModelAttribute Anuncio anuncio) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        anuncio.setId(id);
        anuncioRepository.save(anuncio);
        return "redirect:/produto/" + anuncio.getId();
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || !"ADMIN".equals(usuario.getRole())) {
            return "redirect:/login";
        }
        anuncioRepository.deleteById(id);
        return "redirect:/admin/anuncios";
    }
}