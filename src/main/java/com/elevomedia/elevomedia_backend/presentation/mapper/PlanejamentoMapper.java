package com.elevomedia.elevomedia_backend.presentation.mapper;

import com.elevomedia.elevomedia_backend.domain.model.Planejamento;
import com.elevomedia.elevomedia_backend.presentation.dto.request.PlanejamentoRequestDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.response.PlanejamentoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanejamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Planejamento toEntity(PlanejamentoRequestDTO dto);

    @Mapping(source = "cliente.id", target = "clienteId")
    PlanejamentoResponseDTO toResponse(Planejamento planejamento);
}
