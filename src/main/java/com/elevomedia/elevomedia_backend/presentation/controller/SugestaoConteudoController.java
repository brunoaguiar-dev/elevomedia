package com.elevomedia.elevomedia_backend.presentation.controller;

import com.elevomedia.elevomedia_backend.application.service.SugestaoConteudoService;
import com.elevomedia.elevomedia_backend.presentation.dto.request.AtualizarStatusDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.request.SugestaoConteudoRequestDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.response.SugestaoConteudoResponseDTO;
import com.elevomedia.elevomedia_backend.presentation.mapper.SugestaoConteudoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sugestoes")
@RequiredArgsConstructor
public class SugestaoConteudoController {

    private final SugestaoConteudoService sugestaoConteudoService;
    private final SugestaoConteudoMapper sugestaoConteudoMapper;

    @PostMapping
    public ResponseEntity<SugestaoConteudoResponseDTO> criar(
            @RequestParam Long planejamentoId,
            @RequestBody @Valid SugestaoConteudoRequestDTO dto) {

        var sugestao = sugestaoConteudoMapper.toEntity(dto);
        var salva = sugestaoConteudoService.criar(planejamentoId, sugestao);
        return ResponseEntity.status(HttpStatus.CREATED).body(sugestaoConteudoMapper.toResponse(salva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SugestaoConteudoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sugestaoConteudoMapper.toResponse(sugestaoConteudoService.buscarPorId(id)));
    }

    @GetMapping
    public ResponseEntity<List<SugestaoConteudoResponseDTO>> listarPorPlanejamento(@RequestParam Long planejamentoId) {
        var response = sugestaoConteudoService.listarPorPlanejamento(planejamentoId).stream()
                .map(sugestaoConteudoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SugestaoConteudoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SugestaoConteudoRequestDTO dto) {

        var dadosNovos = sugestaoConteudoMapper.toEntity(dto);
        var atualizada = sugestaoConteudoService.atualizar(id, dadosNovos);
        return ResponseEntity.ok(sugestaoConteudoMapper.toResponse(atualizada));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SugestaoConteudoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusDTO dto) {

        var atualizada = sugestaoConteudoService.atualizarStatus(id, dto.status());
        return ResponseEntity.ok(sugestaoConteudoMapper.toResponse(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sugestaoConteudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/planejamento/{planejamentoId}")
    public ResponseEntity<Void> deletarPorPlanejamento(@PathVariable Long planejamentoId) {
        sugestaoConteudoService.deletarPorPlanejamento(planejamentoId);
        return ResponseEntity.noContent().build();
    }
}
