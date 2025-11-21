package com.fiap.careermap.controller;

import com.fiap.careermap.model.Trilha;
import com.fiap.careermap.service.TrilhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ia")
public class AIController {

    @Autowired
    private TrilhaService trilhaService;

    @GetMapping("/explicar-trilha/{trilhaId}")
    public ResponseEntity<String> explicarTrilha(@PathVariable Long trilhaId) {
        Trilha trilha = trilhaService.buscarPorId(trilhaId);

        if (trilha.getExplicacaoIA() == null || trilha.getExplicacaoIA().isEmpty()) {
            return ResponseEntity.accepted().body("A explicação da IA está sendo gerada. Tente novamente em alguns instantes.");
        }

        return ResponseEntity.ok(trilha.getExplicacaoIA());
    }
}
