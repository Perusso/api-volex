package com.dev.api_volex;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TesteController {

    @GetMapping("/status")
    public Map<String, String> verificarStatus() {
        // Retornando um JSON simples
        Map<String, String> resposta = new HashMap<>();
        resposta.put("status", "online");
        resposta.put("mensagem", "API rodando com sucesso!");
        resposta.put("banco", "MySQL conectado via Docker");

        return resposta;
    }
}