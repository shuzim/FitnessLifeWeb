package com.fitnesslife.fitness.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LifeController {

    private static final Logger logger = LoggerFactory.getLogger(LifeController.class);

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