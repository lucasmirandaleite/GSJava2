package com.fiap.careermap.controller;

import com.fiap.careermap.dto.CompetenciaDTO;
import com.fiap.careermap.model.Competencia;
import com.fiap.careermap.service.CompetenciaService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/competencias")
public class CompetenciaController {

    @Autowired
    private CompetenciaService competenciaService;

    @GetMapping
    public ResponseEntity<Page<Competencia>> listarCompetencias(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(competenciaService.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competencia> buscarCompetenciaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(competenciaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Competencia> criarCompetencia(@RequestBody @Valid CompetenciaDTO dto) {
        Competencia novaCompetencia = competenciaService.criarCompetencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCompetencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Competencia> atualizarCompetencia(@PathVariable Long id, @RequestBody @Valid CompetenciaDTO dto) {
        Competencia competenciaAtualizada = competenciaService.atualizarCompetencia(id, dto);
        return ResponseEntity.ok(competenciaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCompetencia(@PathVariable Long id) {
        competenciaService.deletarCompetencia(id);
        return ResponseEntity.noContent().build();
    }
}
