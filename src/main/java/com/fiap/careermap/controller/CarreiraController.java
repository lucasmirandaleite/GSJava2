package com.fiap.careermap.controller;

import com.fiap.careermap.dto.CarreiraDTO;
import com.fiap.careermap.model.Carreira;
import com.fiap.careermap.service.CarreiraService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carreiras")
public class CarreiraController {

    @Autowired
    private CarreiraService carreiraService;

    @GetMapping
    public ResponseEntity<Page<Carreira>> listarCarreiras(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(carreiraService.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carreira> buscarCarreiraPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carreiraService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Carreira> criarCarreira(@RequestBody @Valid CarreiraDTO dto) {
        Carreira novaCarreira = carreiraService.criarCarreira(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCarreira);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carreira> atualizarCarreira(@PathVariable Long id, @RequestBody @Valid CarreiraDTO dto) {
        Carreira carreiraAtualizada = carreiraService.atualizarCarreira(id, dto);
        return ResponseEntity.ok(carreiraAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarreira(@PathVariable Long id) {
        carreiraService.deletarCarreira(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/compatibilidade")
    public ResponseEntity<Double> calcularCompatibilidade(@PathVariable Long id, @RequestBody List<Long> competenciasUsuarioIds) {
        double compatibilidade = carreiraService.calcularCompatibilidade(id, competenciasUsuarioIds);
        return ResponseEntity.ok(compatibilidade);
    }
}
