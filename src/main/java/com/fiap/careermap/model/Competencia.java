package com.fiap.careermap.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "T_CM_COMPETENCIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_competencia")
    private Long id;

    @Column(name = "nm_competencia", nullable = false, unique = true)
    private String nome;

    @Column(name = "ds_competencia", nullable = false)
    private String descricao;
}
