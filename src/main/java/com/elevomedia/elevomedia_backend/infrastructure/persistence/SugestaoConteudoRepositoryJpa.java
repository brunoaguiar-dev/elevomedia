package com.elevomedia.elevomedia_backend.infrastructure.persistence;

import com.elevomedia.elevomedia_backend.domain.model.SugestaoConteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SugestaoConteudoRepositoryJpa extends JpaRepository<SugestaoConteudo, Long> {

    List<SugestaoConteudo> findByPlanejamentoId(Long planejamentoId);

    @Transactional
    void deleteByPlanejamentoId(Long planejamentoId);
}
