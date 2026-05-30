package br.com.backend.backend.repository;

import br.com.backend.backend.domain.entities.SolicitacaoProduto;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoProdutoRepository extends JpaRepository<SolicitacaoProduto, Long> {
    List<SolicitacaoProduto> findByStatus(StatusSolicitacao status);

    List<SolicitacaoProduto> findBySolicitante(String solicitante);

    List<SolicitacaoProduto> findByStatusOrderByDataCriacaoDesc(StatusSolicitacao status);

    List<SolicitacaoProduto> findAllByOrderByDataCriacaoDesc();
}
