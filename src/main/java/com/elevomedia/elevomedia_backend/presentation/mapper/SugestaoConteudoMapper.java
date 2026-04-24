package com.elevomedia.elevomedia_backend.presentation.mapper;

import com.elevomedia.elevomedia_backend.domain.model.SugestaoConteudo;
import com.elevomedia.elevomedia_backend.presentation.dto.request.SugestaoConteudoRequestDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.response.SugestaoConteudoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SugestaoConteudoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "planejamento", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    SugestaoConteudo toEntity(SugestaoConteudoRequestDTO dto);

    @Mapping(source = "planejamento.id", target = "planejamentoId")
    SugestaoConteudoResponseDTO toResponse(SugestaoConteudo sugestao);

}
