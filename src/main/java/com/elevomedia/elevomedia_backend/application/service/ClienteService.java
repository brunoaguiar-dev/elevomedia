package com.elevomedia.elevomedia_backend.application.service;

import com.elevomedia.elevomedia_backend.domain.exception.ConflitoException;
import com.elevomedia.elevomedia_backend.domain.exception.NaoEncontradoException;
import com.elevomedia.elevomedia_backend.domain.model.Cliente;
import com.elevomedia.elevomedia_backend.domain.model.Usuario;
import com.elevomedia.elevomedia_backend.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente criar(Long usuarioId, Cliente cliente) {
        clienteRepository.buscarPorNomeNegocioEUsuarioId(cliente.getNomeNegocio(), usuarioId)
                .ifPresent(c -> {
                    throw new ConflitoException("Já existe um cliente com esse nome.");
                });

        cliente.setUsuario(new Usuario(usuarioId));
        return clienteRepository.salvar(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado."));
    }

    public List<Cliente> listarPorUsuario(Long usuarioId) {
        return clienteRepository.listarPorUsuario(usuarioId);
    }

    @Transactional
    public Cliente atualizar(Long id, Cliente dadosNovos) {
        Cliente cliente = buscarPorId(id);
        cliente.atualizarCom(dadosNovos);
        return clienteRepository.salvar(cliente);
    }

    @Transactional
    public void desativar(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        clienteRepository.salvar(cliente);
    }
}