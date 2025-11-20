package com.futebolcamisas.repository;

import com.futebolcamisas.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    List<ItemCarrinho> findByCarrinhoId(Long carrinhoId);
    // Métodos adicionais podem ser definidos aqui
}
