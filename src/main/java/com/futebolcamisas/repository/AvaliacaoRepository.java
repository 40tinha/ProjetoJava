package com.futebolcamisas.repository;

import com.futebolcamisas.model.Avaliacao;
import com.futebolcamisas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByProdutoId(Long produtoId);
    List<Avaliacao> findByProdutoIdOrderByDataCriacaoDesc(Long produtoId);
}
