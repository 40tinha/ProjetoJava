package com.futebolcamisas.repository;

import com.futebolcamisas.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    // Métodos adicionais podem ser definidos aqui
}
