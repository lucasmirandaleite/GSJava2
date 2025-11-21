package com.fiap.careermap.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CursoDTO {
    private Long id;

    @NotBlank(message = "{curso.nome.notblank}")
    private String nome;

    @NotBlank(message = "{curso.descricao.notblank}")
    private String descricao;

    @NotNull(message = "{curso.duracao.notnull}")
    private Integer duracaoHoras;

    private List<Long> competenciasAdquiridasIds;
}
