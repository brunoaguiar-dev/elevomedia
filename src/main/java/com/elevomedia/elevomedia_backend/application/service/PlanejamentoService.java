package com.elevomedia.elevomedia_backend.application.service;

import com.elevomedia.elevomedia_backend.domain.exception.ConflitoException;
import com.elevomedia.elevomedia_backend.domain.exception.NaoEncontradoException;
import com.elevomedia.elevomedia_backend.domain.model.Cliente;
import com.elevomedia.elevomedia_backend.domain.model.Planejamento;
import com.elevomedia.elevomedia_backend.domain.repository.PlanejamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;

    @Transactional
    public Planejamento criar(Long clienteId, Planejamento dados) {
        planejamentoRepository.buscarPorClienteMesAno(clienteId, dados.getMes(), dados.getAno())
                .ifPresent(p -> { throw new ConflitoException("Já existe um planejamento para este cliente neste mês/ano."); });
        dados.setCliente(new Cliente(clienteId));
        return planejamentoRepository.salvar(dados);
    }

    public Planejamento buscarPorId(Long id) {
        return planejamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Planejamento não encontrado."));
    }

    public List<Planejamento> listarPorCliente(Long clienteId) {
        return planejamentoRepository.listarPorCliente(clienteId);
    }

    public Planejamento buscarPorClienteMesAno(Long clienteId, Integer mes, Integer ano) {
        return planejamentoRepository.buscarPorClienteMesAno(clienteId, mes, ano)
                .orElseThrow(() -> new NaoEncontradoException("Planejamento não encontrado para este cliente neste mês/ano."));
    }

    @Transactional
    public Planejamento atualizar(Long id, String observacoesGeracao) {
        Planejamento planejamento = buscarPorId(id);
        planejamento.setObservacoesGeracao(observacoesGeracao);
        return planejamentoRepository.salvar(planejamento);
    }

    @Transactional
    public Planejamento atualizarStatus(Long id, String novoStatus) {
        Planejamento planejamento = buscarPorId(id);
        planejamento.setStatus(novoStatus);
        return planejamentoRepository.salvar(planejamento);
    }
}
