package com.fitnesslife.fitness.controller;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
@CrossOrigin(origins = "*")
public class GeminiController {

    // teste api
    private static final String API_KEY = "AIzaSyCSOCMD-wiWnBCbg1glwD2wNlyDq4ozBro";

    @PostMapping("/respostas")
    public String geminiResponse(@RequestBody Map<String, String> body) {
        String pergunta = body.get("pergunta");

        if (pergunta == null || pergunta.trim().isEmpty()) {
            return "Faça uma pergunta!";
        }

        try {
            // AQUI ESTÁ O QUE VOCÊ QUERIA: client criado com a chave dentro
            Client client = Client.builder()
                    .apiKey(API_KEY)
                    .build();

            // MANTIVE EXATAMENTE COMO VOCÊ PEDIU
            GenerateContentResponse response =
                    client.models.generateContent("gemini-2.0-flash", pergunta, null);

            return response.text();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }
    }
}