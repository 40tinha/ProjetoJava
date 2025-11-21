package com.futebolcamisas.repository;

import com.futebolcamisas.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    // Consulta para buscar todas avaliações de um anúncio específico
    List<Avaliacao> findByAnuncioId(Long anuncioId);
}
