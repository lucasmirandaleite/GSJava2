package com.fiap.careermap.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "T_CM_TRILHA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trilha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trilha")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_carreira", nullable = false)
    private Carreira carreiraAlvo;

    @ManyToMany
    @JoinTable(
        name = "T_CM_TRILHA_CURSO",
        joinColumns = @JoinColumn(name = "id_trilha"),
        inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Curso> cursosRecomendados;

    @Column(name = "dt_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "ds_explicacao_ia", columnDefinition = "TEXT")
    private String explicacaoIA;
}
