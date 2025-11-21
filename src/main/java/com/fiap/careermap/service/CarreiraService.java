package com.fiap.careermap.service;

import com.fiap.careermap.dto.CarreiraDTO;
import com.fiap.careermap.model.Carreira;
import com.fiap.careermap.model.Competencia;
import com.fiap.careermap.repository.CarreiraRepository;
import com.fiap.careermap.repository.CompetenciaRepository;
import com.fiap.careermap.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreiraService {

    @Autowired
    private CarreiraRepository carreiraRepository;

    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Cacheable("carreiras")
    public Page<Carreira> listarTodas(Pageable pageable) {
        return carreiraRepository.findAll(pageable);
    }

    public Carreira criarCarreira(CarreiraDTO dto) {
        Carreira carreira = new Carreira();
        carreira.setNome(dto.getNome());
        carreira.setDescricao(dto.getDescricao());

        List<Competencia> competencias = competenciaRepository.findAllById(dto.getCompetenciasRequeridasIds());
        carreira.setCompetenciasRequeridas(competencias);

        return carreiraRepository.save(carreira);
    }

    public Carreira buscarPorId(Long id) {
        return carreiraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carreira", "id", id));
    }

    public Carreira atualizarCarreira(Long id, CarreiraDTO dto) {
        Carreira carreira = buscarPorId(id);
        carreira.setNome(dto.getNome());
        carreira.setDescricao(dto.getDescricao());

        List<Competencia> competencias = competenciaRepository.findAllById(dto.getCompetenciasRequeridasIds());
        carreira.setCompetenciasRequeridas(competencias);

        return carreiraRepository.save(carreira);
    }

    public void deletarCarreira(Long id) {
        carreiraRepository.deleteById(id);
    }

    // Lógica de Compatibilidade de Carreira (simulação)
    public double calcularCompatibilidade(Long carreiraId, List<Long> competenciasUsuarioIds) {
        Carreira carreira = buscarPorId(carreiraId);
        List<Long> competenciasRequeridasIds = carreira.getCompetenciasRequeridas().stream()
                .map(Competencia::getId)
                .collect(Collectors.toList());

        long competenciasAtendidas = competenciasRequeridasIds.stream()
                .filter(competenciasUsuarioIds::contains)
                .count();

        if (competenciasRequeridasIds.isEmpty()) {
            return 0.0;
        }

        return (double) competenciasAtendidas / competenciasRequeridasIds.size() * 100;
    }
}
