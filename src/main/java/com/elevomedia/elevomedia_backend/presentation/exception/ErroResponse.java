package com.elevomedia.elevomedia_backend.presentation.exception;

import java.time.LocalDateTime;

public record ErroResponse(
        int status,
        String mensagem,
        LocalDateTime momento
) {}
