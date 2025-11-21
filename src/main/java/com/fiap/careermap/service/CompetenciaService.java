package com.fiap.careermap.service;

import com.fiap.careermap.dto.CompetenciaDTO;
import com.fiap.careermap.model.Competencia;
import com.fiap.careermap.repository.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompetenciaService {

    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Cacheable("competencias")
    public Page<Competencia> listarTodas(Pageable pageable) {
        return competenciaRepository.findAll(pageable);
    }

    public Competencia criarCompetencia(CompetenciaDTO dto) {
        Competencia competencia = new Competencia();
        competencia.setNome(dto.getNome());
        competencia.setDescricao(dto.getDescricao());
        return competenciaRepository.save(competencia);
    }

    public Competencia buscarPorId(Long id) {
        return competenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competência não encontrada."));
    }

    public Competencia atualizarCompetencia(Long id, CompetenciaDTO dto) {
        Competencia competencia = buscarPorId(id);
        competencia.setNome(dto.getNome());
        competencia.setDescricao(dto.getDescricao());
        return competenciaRepository.save(competencia);
    }

    public void deletarCompetencia(Long id) {
        competenciaRepository.deleteById(id);
    }
}
