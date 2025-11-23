package com.futebolcamisas.controller;

import com.futebolcamisas.model.Usuario;
import com.futebolcamisas.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        logger.info("📝 Acessando formulário de login");
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        logger.info("🔐 Tentando fazer login com email: {}", email);

        try {
            if (email == null || email.trim().isEmpty()) {
                logger.warn("❌ Email vazio");
                redirectAttributes.addFlashAttribute("erro", "❌ Email é obrigatório!");
                return "redirect:/login";
            }

            if (senha == null || senha.trim().isEmpty()) {
                logger.warn("❌ Senha vazia");
                redirectAttributes.addFlashAttribute("erro", "❌ Senha é obrigatória!");
                return "redirect:/login";
            }

            Usuario usuario = usuarioService.login(email, senha);

            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioLogado", usuario);  // ✅ Adicionado

            logger.info("✅ Login bem-sucedido para: {}", email);
            redirectAttributes.addFlashAttribute("sucesso", "✅ Login realizado com sucesso!");
            return "redirect:/";

        } catch (RuntimeException e) {
            logger.warn("❌ Erro ao fazer login: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("erro", "❌ " + e.getMessage());
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("❌ ERRO inesperado ao fazer login: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("erro", "❌ Erro ao fazer login!");
            return "redirect:/login";
        }
    }

    @GetMapping("/cadastro")
    public String cadastroForm(Model model) {
        logger.info("📝 Acessando formulário de cadastro");
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastro(@Valid Usuario usuario,
                           BindingResult result,
                           @RequestParam String confirmarSenha,
                           RedirectAttributes redirectAttributes) {

        logger.info("📝 Tentando cadastrar novo usuário: {}", usuario.getEmail());

        // Validação do Bean Validation
        if (result.hasErrors()) {
            logger.warn("❌ Erros de validação no cadastro");
            result.getAllErrors().forEach(error ->
                    logger.warn("  - {}", error.getDefaultMessage())
            );
            return "cadastro";
        }

        // Validação de email vazio
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            logger.warn("❌ Email vazio no cadastro");
            redirectAttributes.addFlashAttribute("erro", "❌ Email é obrigatório!");
            return "redirect:/cadastro";
        }

        // Validação de senha vazia
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            logger.warn("❌ Senha vazia no cadastro");
            redirectAttributes.addFlashAttribute("erro", "❌ Senha é obrigatória!");
            return "redirect:/cadastro";
        }

        // Validação de confirmação de senha
        if (!usuario.getSenha().equals(confirmarSenha)) {
            logger.warn("❌ Senhas não coincidem para: {}", usuario.getEmail());
            redirectAttributes.addFlashAttribute("erro", "❌ As senhas não coincidem!");
            return "redirect:/cadastro";
        }

        // Validação de força de senha (mínimo 6 caracteres)
        if (usuario.getSenha().length() < 6) {
            logger.warn("❌ Senha muito fraca para: {}", usuario.getEmail());
            redirectAttributes.addFlashAttribute("erro", "❌ Senha deve ter no mínimo 6 caracteres!");
            return "redirect:/cadastro";
        }

        try {
            usuarioService.cadastrar(usuario);
            logger.info("✅ Usuário cadastrado com sucesso: {}", usuario.getEmail());
            redirectAttributes.addFlashAttribute("sucesso", "✅ Cadastro realizado com sucesso! Faça login.");
            return "redirect:/login";

        } catch (RuntimeException e) {
            logger.warn("❌ Erro ao cadastrar: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("erro", "❌ " + e.getMessage());
            return "redirect:/cadastro";
        } catch (Exception e) {
            logger.error("❌ ERRO inesperado ao cadastrar: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("erro", "❌ Erro ao cadastrar!");
            return "redirect:/cadastro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        logger.info("👋 Fazendo logout");

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            logger.info("👋 Logout para: {}", usuario.getEmail());
        }

        session.invalidate();
        redirectAttributes.addFlashAttribute("sucesso", "✅ Logout realizado com sucesso!");
        return "redirect:/";
    }
}
