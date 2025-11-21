package com.fiap.careermap.controller;

import com.fiap.careermap.dto.CursoDTO;
import com.fiap.careermap.model.Curso;
import com.fiap.careermap.service.CursoService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<Page<Curso>> listarCursos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(cursoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Curso> criarCurso(@RequestBody @Valid CursoDTO dto) {
        Curso novoCurso = cursoService.criarCurso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoDTO dto) {
        Curso cursoAtualizado = cursoService.atualizarCurso(id, dto);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        cursoService.deletarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
