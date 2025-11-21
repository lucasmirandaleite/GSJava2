package com.fiap.careermap.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "T_CM_CURSO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long id;

    @Column(name = "nm_curso", nullable = false)
    private String nome;

    @Column(name = "ds_descricao", nullable = false)
    private String descricao;

    @Column(name = "nr_duracao_horas")
    private Integer duracaoHoras;

    @ManyToMany
    @JoinTable(
        name = "T_CM_CURSO_COMPETENCIA",
        joinColumns = @JoinColumn(name = "id_curso"),
        inverseJoinColumns = @JoinColumn(name = "id_competencia")
    )
    private List<Competencia> competenciasAdquiridas;
}
