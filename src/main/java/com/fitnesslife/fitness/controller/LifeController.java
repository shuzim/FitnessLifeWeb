package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.Usuario;
import com.fitnesslife.fitness.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LifeController {

    private static final Logger logger = LoggerFactory.getLogger(LifeController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioApiController usuarioApiController;

    @GetMapping("/")
    public String home() {
        logger.info("Acessando a página inicial, redirecionando para /login");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String welcome() {
        logger.info("Acessando a página de login");
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        logger.info("Acessando a página de cadastro");
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(
            @RequestParam String nome,
            @RequestParam String sobrenome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam Integer idade,
            @RequestParam String sexo,
            @RequestParam Double altura,
            @RequestParam Double peso,
            Model model
    ) {
        logger.info("Processando cadastro para email: {}", email);

        // Verificar se o email já existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            logger.warn("Email já cadastrado: {}", email);
            model.addAttribute("error", "Email já cadastrado");
            return "cadastro";
        }

        // Criar novo usuário com senha hasheada
        Usuario usuario = new Usuario(nome, sobrenome, email, passwordEncoder.encode(senha), idade, sexo, altura, peso);

        // Chamar o método do UsuarioApiController diretamente
        ResponseEntity<Usuario> response = usuarioApiController.criarUsuario(usuario);
        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Usuário cadastrado com sucesso: {}", email);
            model.addAttribute("success", true); // Adiciona atributo para exibir modal de sucesso
            return "cadastro";
        } else {
            logger.error("Erro ao cadastrar usuário: {}", email);
            model.addAttribute("error", "Erro ao cadastrar usuário");
            return "cadastro";
        }
    }

    @GetMapping("/menu")
    public String menu() {
        logger.info("Acessando a página de menu");
        return "menu";
    }

    @GetMapping("/perfilnutricional")
    public String perfilNutricional() {
        logger.info("Acessando a página de perfil nutricional");
        return "perfilNutricional";
    }

    @GetMapping("/planorefeicoes")
    public String planoRefeicoes() {
        logger.info("Acessando a página de plano de refeições");
        return "planorefeicoes";
    }

    @GetMapping("/rastreamento-alimento")
    public String rastreamentoAlimento() {
        logger.info("Acessando a página de rastreamento de alimentos");
        return "rastreamentoAlimento";
    }

    @GetMapping("/receitas-saudaveis")
    public String receitassaudaveis() {
        logger.info("Acessando a página de receitas saudáveis");
        return "receitasSaudaveis";
    }
}