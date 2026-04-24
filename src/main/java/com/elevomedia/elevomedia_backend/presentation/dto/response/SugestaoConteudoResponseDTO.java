package com.elevomedia.elevomedia_backend.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SugestaoConteudoResponseDTO(

        Long id,
        Long planejamentoId,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataPublicacao,

        String tituloData,
        String formatoPost,
        String legenda,
        String hashtags,
        String chamadaAcao,
        String roteiroVideo,
        String instrucaoDesign,
        String status,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
) {}
