package com.fitnesslife.fitness.controller;

import com.fitnesslife.fitness.model.Pergunta;
import com.fitnesslife.fitness.repository.PerguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PerguntaController {

    private static final Logger logger = LoggerFactory.getLogger(PerguntaController.class);
    private final PerguntaRepository perguntaRepository;

    public PerguntaController(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @PostMapping("/perguntas")
    public ResponseEntity<Pergunta> criarPergunta(@RequestBody Pergunta pergunta) {
        logger.info("Recebendo preferências alimentares do usuário.");

        if (pergunta.getGostaFrutas() == null || pergunta.getGostaVerduras() == null) {
            logger.warn("Campos obrigatórios ausentes nas respostas do usuário.");
            return ResponseEntity.badRequest().body(null);
        }

        Pergunta novaPergunta = perguntaRepository.save(pergunta);
        novaPergunta.setMensagemIA("Respostas recebidas! A IA vai gerar uma receita personalizada.");
        logger.info("Respostas salvas com sucesso.");
        return ResponseEntity.ok(novaPergunta);
    }
}