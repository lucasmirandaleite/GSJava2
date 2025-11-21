package com.fiap.careermap.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CarreiraDTO {
    private Long id;

    @NotBlank(message = "{carreira.nome.notblank}")
    private String nome;

    @NotBlank(message = "{carreira.descricao.notblank}")
    private String descricao;

    private List<Long> competenciasRequeridasIds;
}
