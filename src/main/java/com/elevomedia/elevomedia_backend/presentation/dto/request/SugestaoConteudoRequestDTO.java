package com.elevomedia.elevomedia_backend.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SugestaoConteudoRequestDTO(

        @NotNull
        LocalDate dataPublicacao,

        @NotBlank
        String formatoPost,

        String tituloData,
        String legenda,
        String hashtags,
        String chamadaAcao,
        String roteiroVideo,
        String instrucaoDesign
) {}
