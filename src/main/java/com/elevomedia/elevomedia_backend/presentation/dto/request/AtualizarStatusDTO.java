package com.elevomedia.elevomedia_backend.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarStatusDTO(

        @NotBlank
        String status
) {}
