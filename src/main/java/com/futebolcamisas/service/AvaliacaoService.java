package com.futebolcamisas.service;

import com.futebolcamisas.model.Avaliacao;
import com.futebolcamisas.model.Produto;
import com.futebolcamisas.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> buscarAvaliacoesPorProduto(Long produtoId) {
        return avaliacaoRepository.findByProdutoIdOrderByDataCriacaoDesc(produtoId);
    }

    public Double calcularMediaNotasProduto(Long produtoId) {
        List<Avaliacao> avaliacoes = buscarAvaliacoesPorProduto(produtoId);
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        return avaliacoes.stream()
                .mapToInt(Avaliacao::getEstrelas)
                .average()
                .orElse(0.0);
    }

    public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }


    public Avaliacao atualizarAvaliacao(Long id, Avaliacao avaliacaoAtualizada) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
        avaliacao.setAutor(avaliacaoAtualizada.getAutor());
        avaliacao.setTexto(avaliacaoAtualizada.getTexto());
        avaliacao.setEstrelas(avaliacaoAtualizada.getEstrelas());
        return avaliacaoRepository.save(avaliacao);
    }

    public void deletarAvaliacao(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}
