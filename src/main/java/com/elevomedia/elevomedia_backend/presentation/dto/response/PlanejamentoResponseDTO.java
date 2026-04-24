package com.elevomedia.elevomedia_backend.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PlanejamentoResponseDTO(

        Long id,
        Long clienteId,
        Integer mes,
        Integer ano,
        String status,
        String observacoesGeracao,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
) {}
