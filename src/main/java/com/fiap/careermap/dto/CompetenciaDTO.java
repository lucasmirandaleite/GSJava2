package com.fiap.careermap.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompetenciaDTO {
    private Long id;

    @NotBlank(message = "{competencia.nome.notblank}")
    private String nome;

    @NotBlank(message = "{competencia.descricao.notblank}")
    private String descricao;
}
