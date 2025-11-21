package com.fiap.careermap.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "T_CM_CARREIRA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carreira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carreira")
    private Long id;

    @Column(name = "nm_carreira", nullable = false, unique = true)
    private String nome;

    @Column(name = "ds_carreira", nullable = false)
    private String descricao;

    @ManyToMany
    @JoinTable(
        name = "T_CM_CARREIRA_COMPETENCIA",
        joinColumns = @JoinColumn(name = "id_carreira"),
        inverseJoinColumns = @JoinColumn(name = "id_competencia")
    )
    private List<Competencia> competenciasRequeridas;
}
