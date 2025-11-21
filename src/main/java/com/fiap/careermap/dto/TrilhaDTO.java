package com.fiap.careermap.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrilhaDTO {
    private Long id;

    @NotNull(message = "{trilha.usuario.notnull}")
    private Long usuarioId;

    @NotNull(message = "{trilha.carreira.notnull}")
    private Long carreiraAlvoId;

    private List<Long> cursosRecomendadosIds;

    private LocalDateTime dataCriacao;

    private String explicacaoIA;
}
