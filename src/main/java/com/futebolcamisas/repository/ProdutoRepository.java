package com.futebolcamisas.repository;

import com.futebolcamisas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // usado na busca com filtros (time + marca)
    List<Produto> findByTimeContainingIgnoreCaseAndMarcaContainingIgnoreCase(String time, String marca);

    // opcionais, úteis para filtros simples
    List<Produto> findByTime(String time);

    List<Produto> findByMarca(String marca);
}
