package com.elevomedia.elevomedia_backend.application.service;

import com.elevomedia.elevomedia_backend.domain.exception.ConflitoException;
import com.elevomedia.elevomedia_backend.domain.exception.NaoEncontradoException;
import com.elevomedia.elevomedia_backend.domain.model.Cliente;
import com.elevomedia.elevomedia_backend.domain.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    @Test
    void deveCriarCliente_quandoNomeNaoExiste() {
        // ARRANGE
        var cliente = new Cliente();
        cliente.setNomeNegocio("Restaurante do João");

        when(clienteRepository.buscarPorNomeNegocioEUsuarioId("Restaurante do João", 1L))
                .thenReturn(Optional.empty());
        when(clienteRepository.salvar(any(Cliente.class)))
                .thenReturn(cliente);

        // ACT
        var resultado = clienteService.criar(1L, cliente);

        // ASSERT
        assertThat(resultado.getNomeNegocio()).isEqualTo("Restaurante do João");
        verify(clienteRepository).salvar(any(Cliente.class));
    }

    @Test
    void deveLancarConflito_quandoNomeJaExiste() {
        // ARRANGE
        var clienteExistente = new Cliente();
        clienteExistente.setNomeNegocio("Restaurante do João");

        when(clienteRepository.buscarPorNomeNegocioEUsuarioId("Restaurante do João", 1L))
                .thenReturn(Optional.of(clienteExistente));

        var novoCliente = new Cliente();
        novoCliente.setNomeNegocio("Restaurante do João");

        // ACT + ASSERT
        assertThatThrownBy(() -> clienteService.criar(1L, novoCliente))
                .isInstanceOf(ConflitoException.class)
                .hasMessage("Já existe um cliente com esse nome.");

        verify(clienteRepository, never()).salvar(any());
    }

    @Test
    void deveLancarNaoEncontrado_quandoClienteNaoExiste() {
        // ARRANGE
        when(clienteRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThatThrownBy(() -> clienteService.buscarPorId(99L))
                .isInstanceOf(NaoEncontradoException.class)
                .hasMessage("Cliente não encontrado.");
    }

    @Test
    void deveRetornarApenasAtivos_quandoAtivoForNulo() {
        // ARRANGE
        var ativo = new Cliente();
        ativo.setAtivo(true);

        var inativo = new Cliente();
        inativo.setAtivo(false);

        when(clienteRepository.listarPorUsuario(1L)).thenReturn(List.of(ativo, inativo));

        // ACT — sem passar ativo (null), o service deve assumir true por padrão
        var resultado = clienteService.listarPorUsuario(1L, null);

        // ASSERT
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getAtivo()).isTrue();
    }

    @Test
    void deveRetornarApenasInativos_quandoAtivoForFalse() {
        // ARRANGE
        var ativo = new Cliente();
        ativo.setAtivo(true);

        var inativo = new Cliente();
        inativo.setAtivo(false);

        when(clienteRepository.listarPorUsuario(1L)).thenReturn(List.of(ativo, inativo));

        // ACT
        var resultado = clienteService.listarPorUsuario(1L, false);

        // ASSERT
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getAtivo()).isFalse();
    }

    @Test
    void deveDesativarCliente() {
        // ARRANGE
        var cliente = new Cliente();
        cliente.setAtivo(true);

        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.salvar(any())).thenReturn(cliente);

        // ACT
        clienteService.desativar(1L);

        // ASSERT
        assertThat(cliente.getAtivo()).isFalse();
        verify(clienteRepository).salvar(cliente);
    }

    @Test
    void deveReativarCliente() {
        // ARRANGE
        var cliente = new Cliente();
        cliente.setAtivo(false);

        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.salvar(any())).thenReturn(cliente);

        // ACT
        clienteService.reativar(1L);

        // ASSERT
        assertThat(cliente.getAtivo()).isTrue();
        verify(clienteRepository).salvar(cliente);
    }
}
