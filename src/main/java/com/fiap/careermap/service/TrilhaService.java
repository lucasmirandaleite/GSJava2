package com.fiap.careermap.service;

import com.fiap.careermap.dto.TrilhaDTO;
import com.fiap.careermap.model.Carreira;
import com.fiap.careermap.model.Curso;
import com.fiap.careermap.model.Trilha;
import com.fiap.careermap.model.Usuario;
import com.fiap.careermap.repository.CarreiraRepository;
import com.fiap.careermap.repository.CursoRepository;
import com.fiap.careermap.repository.TrilhaRepository;
import com.fiap.careermap.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrilhaService {

    @Autowired
    private TrilhaRepository trilhaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarreiraRepository carreiraRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AIService aiService;

    public Page<Trilha> listarTodas(Pageable pageable) {
        return trilhaRepository.findAll(pageable);
    }

    public Trilha criarTrilha(TrilhaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Carreira carreira = carreiraRepository.findById(dto.getCarreiraAlvoId())
                .orElseThrow(() -> new RuntimeException("Carreira não encontrada."));

        // Lógica de recomendação de cursos (simulada)
        List<Curso> cursosRecomendados = cursoRepository.findAll().stream()
                .filter(curso -> curso.getCompetenciasAdquiridas().stream()
                        .anyMatch(competencia -> carreira.getCompetenciasRequeridas().contains(competencia)))
                .limit(5) // Limita a 5 cursos para a trilha
                .collect(Collectors.toList());

        Trilha trilha = new Trilha();
        trilha.setUsuario(usuario);
        trilha.setCarreiraAlvo(carreira);
        trilha.setCursosRecomendados(cursosRecomendados);

        // Chamada assíncrona para gerar a explicação da IA
        aiService.gerarExplicacaoTrilhaAsync(trilha);

        return trilhaRepository.save(trilha);
    }

    public Trilha buscarPorId(Long id) {
        return trilhaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trilha não encontrada."));
    }

    public void deletarTrilha(Long id) {
        trilhaRepository.deleteById(id);
    }
}
