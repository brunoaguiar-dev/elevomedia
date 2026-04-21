package com.elevomedia.elevomedia_backend.presentation.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErroResponse(
        int status,
        String mensagem,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime momento
) {}
