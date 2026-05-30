package br.com.backend.backend.service;

import br.com.backend.backend.domain.entities.SolicitacaoProduto;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import br.com.backend.backend.dto.request.AtualizarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.request.CriarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.response.SolicitacaoProdutoResponse;
import br.com.backend.backend.exception.RecursoNaoEncontradoException;
import br.com.backend.backend.repository.SolicitacaoProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SolicitacaoProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoProdutoService.class);

    private final SolicitacaoProdutoRepository repository;

    public SolicitacaoProdutoService(SolicitacaoProdutoRepository repository) {
        this.repository = repository;
    }

    public SolicitacaoProdutoResponse criar(CriarSolicitacaoProdutoRequest request) {
        logger.info("Criando nova solicitação de produto: {}", request.getNomeProduto());

        SolicitacaoProduto solicitacao = new SolicitacaoProduto(
                request.getNomeProduto(),
                request.getQuantidade(),
                request.getSolicitante(),
                request.getPrioridade(),
                request.getObservacao()
        );

        SolicitacaoProduto salva = repository.save(solicitacao);
        logger.info("Solicitação criada com sucesso. ID: {}", salva.getId());

        return new SolicitacaoProdutoResponse(salva);
    }

    @Transactional(readOnly = true)
    public SolicitacaoProdutoResponse buscarPorId(Long id) {
        logger.debug("Buscando solicitação com ID: {}", id);

        SolicitacaoProduto solicitacao = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Solicitação não encontrada. ID: {}", id);
                    return new RecursoNaoEncontradoException("Solicitação não encontrada");
                });

        return new SolicitacaoProdutoResponse(solicitacao);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoProdutoResponse> listar() {
        logger.debug("Listando todas as solicitações");
        return repository.findAllByOrderByDataCriacaoDesc()
                .stream()
                .map(SolicitacaoProdutoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoProdutoResponse> listarPorStatus(StatusSolicitacao status) {
        logger.debug("Listando solicitações com status: {}", status);
        return repository.findByStatusOrderByDataCriacaoDesc(status)
                .stream()
                .map(SolicitacaoProdutoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoProdutoResponse> listarPorSolicitante(String solicitante) {
        logger.debug("Listando solicitações do solicitante: {}", solicitante);
        return repository.findBySolicitante(solicitante)
                .stream()
                .map(SolicitacaoProdutoResponse::new)
                .collect(Collectors.toList());
    }

    public SolicitacaoProdutoResponse atualizar(Long id, AtualizarSolicitacaoProdutoRequest request) {
        logger.info("Atualizando solicitação com ID: {}", id);

        SolicitacaoProduto solicitacao = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Solicitação não encontrada para atualização. ID: {}", id);
                    return new RecursoNaoEncontradoException("Solicitação não encontrada");
                });

        solicitacao.setNomeProduto(request.getNomeProduto());
        solicitacao.setQuantidade(request.getQuantidade());
        solicitacao.setSolicitante(request.getSolicitante());
        solicitacao.setPrioridade(request.getPrioridade());
        solicitacao.setStatus(request.getStatus());
        solicitacao.setObservacao(request.getObservacao());

        SolicitacaoProduto atualizada = repository.save(solicitacao);
        logger.info("Solicitação atualizada com sucesso. ID: {}", id);

        return new SolicitacaoProdutoResponse(atualizada);
    }

    public void deletar(Long id) {
        logger.info("Deletando solicitação com ID: {}", id);

        if (!repository.existsById(id)) {
            logger.warn("Solicitação não encontrada para deleção. ID: {}", id);
            throw new RecursoNaoEncontradoException("Solicitação não encontrada");
        }

        repository.deleteById(id);
        logger.info("Solicitação deletada com sucesso. ID: {}", id);
    }
}
