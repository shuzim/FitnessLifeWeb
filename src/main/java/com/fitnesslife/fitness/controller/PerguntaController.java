package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.Pergunta;
import com.fitnesslife.fitness.repository.PerguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerguntaController {

    @GetMapping("/perguntas")
    public String carregarPerguntas() {
        return "perguntas"; // carrega perguntas.html
    }

    private String gostaFrutas;
    private String gostaVerduras;

    public String getGostaFrutas() { return gostaFrutas; }
    public void setGostaFrutas(String gostaFrutas) { this.gostaFrutas = gostaFrutas; }

    public String getGostaVerduras() { return gostaVerduras; }
    public void setGostaVerduras(String gostaVerduras) { this.gostaVerduras = gostaVerduras; }
}
