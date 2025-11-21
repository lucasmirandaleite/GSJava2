package com.fiap.careermap.controller;

import com.fiap.careermap.dto.TrilhaDTO;
import com.fiap.careermap.model.Trilha;
import com.fiap.careermap.service.TrilhaService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trilhas")
public class TrilhaController {

    @Autowired
    private TrilhaService trilhaService;

    @GetMapping
    public ResponseEntity<Page<Trilha>> listarTrilhas(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(trilhaService.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trilha> buscarTrilhaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(trilhaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Trilha> criarTrilha(@RequestBody @Valid TrilhaDTO dto) {
        Trilha novaTrilha = trilhaService.criarTrilha(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTrilha);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTrilha(@PathVariable Long id) {
        trilhaService.deletarTrilha(id);
        return ResponseEntity.noContent().build();
    }
}
