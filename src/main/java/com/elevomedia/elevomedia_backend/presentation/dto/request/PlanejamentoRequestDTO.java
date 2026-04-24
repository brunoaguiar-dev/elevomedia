package com.elevomedia.elevomedia_backend.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PlanejamentoRequestDTO(

        @NotNull
        @Min(1) @Max(12)
        Integer mes,

        @NotNull
        @Min(2024) @Max(2100)
        Integer ano,

        String observacoesGeracao
) {}
