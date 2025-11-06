
package com.fitnesslife.fitness.controller;

import com.google.api.client.util.Value;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/gemini")
public class GeminiController {
    @PostMapping("respostas")
    public String GeminiRespose(@RequestBody Map<String, String> body) {
        String pergunta = body.get("pergunta");

        System.setProperty("GEMINI_API_KEY", "AIzaSyCwNqJ4Ns9kpoYJeC4a-A197ukogAWGjHc");

        Client client = new Client();

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.0-flash", pergunta, null);

        return response.text();
    }

}
