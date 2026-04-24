package com.elevomedia.elevomedia_backend.presentation.controller;

import com.elevomedia.elevomedia_backend.application.service.PlanejamentoService;
import com.elevomedia.elevomedia_backend.presentation.dto.request.AtualizarStatusDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.request.PlanejamentoAtualizarDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.request.PlanejamentoRequestDTO;
import com.elevomedia.elevomedia_backend.presentation.dto.response.PlanejamentoResponseDTO;
import com.elevomedia.elevomedia_backend.presentation.mapper.PlanejamentoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/planejamentos")
@RequiredArgsConstructor
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;
    private final PlanejamentoMapper planejamentoMapper;

    @PostMapping
    public ResponseEntity<PlanejamentoResponseDTO> criar(
            @RequestParam Long clienteId,
            @RequestBody @Valid PlanejamentoRequestDTO dto) {

        var planejamento = planejamentoMapper.toEntity(dto);
        var salvo = planejamentoService.criar(clienteId, planejamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(planejamentoMapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanejamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planejamentoMapper.toResponse(planejamentoService.buscarPorId(id)));
    }

    @GetMapping
    public ResponseEntity<List<PlanejamentoResponseDTO>> listarPorCliente(@RequestParam Long clienteId) {
        var response = planejamentoService.listarPorCliente(clienteId).stream()
                .map(planejamentoMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanejamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PlanejamentoAtualizarDTO dto) {

        var atualizado = planejamentoService.atualizar(id, dto.observacoesGeracao());
        return ResponseEntity.ok(planejamentoMapper.toResponse(atualizado));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PlanejamentoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusDTO dto) {

        var atualizado = planejamentoService.atualizarStatus(id, dto.status());
        return ResponseEntity.ok(planejamentoMapper.toResponse(atualizado));
    }
}
