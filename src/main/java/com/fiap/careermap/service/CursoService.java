package com.fiap.careermap.service;

import com.fiap.careermap.dto.CursoDTO;
import com.fiap.careermap.model.Competencia;
import com.fiap.careermap.model.Curso;
import com.fiap.careermap.repository.CompetenciaRepository;
import com.fiap.careermap.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Cacheable("cursos")
    public Page<Curso> listarTodos(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    public Curso criarCurso(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());
        curso.setDuracaoHoras(dto.getDuracaoHoras());

        List<Competencia> competencias = competenciaRepository.findAllById(dto.getCompetenciasAdquiridasIds());
        curso.setCompetenciasAdquiridas(competencias);

        return cursoRepository.save(curso);
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado."));
    }

    public Curso atualizarCurso(Long id, CursoDTO dto) {
        Curso curso = buscarPorId(id);
        curso.setNome(dto.getNome());
        curso.setDescricao(dto.getDescricao());
        curso.setDuracaoHoras(dto.getDuracaoHoras());

        List<Competencia> competencias = competenciaRepository.findAllById(dto.getCompetenciasAdquiridasIds());
        curso.setCompetenciasAdquiridas(competencias);

        return cursoRepository.save(curso);
    }

    public void deletarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}
