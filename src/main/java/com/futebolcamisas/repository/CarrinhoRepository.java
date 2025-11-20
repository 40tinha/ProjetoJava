package com.futebolcamisas.repository;

import com.futebolcamisas.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByUsuarioId(Long usuarioId);
    // Métodos adicionais podem ser definidos aqui
}
