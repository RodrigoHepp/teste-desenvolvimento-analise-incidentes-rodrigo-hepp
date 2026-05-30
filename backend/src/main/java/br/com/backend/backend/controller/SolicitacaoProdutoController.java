package br.com.backend.backend.controller;

import br.com.backend.backend.domain.enums.StatusSolicitacao;
import br.com.backend.backend.dto.request.AtualizarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.request.CriarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.response.SolicitacaoProdutoResponse;
import br.com.backend.backend.service.SolicitacaoProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
@Tag(name = "Solicitações de Produtos", description = "API para gerenciar solicitações de produtos")
public class SolicitacaoProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoProdutoController.class);

    private final SolicitacaoProdutoService service;

    public SolicitacaoProdutoController(SolicitacaoProdutoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as solicitações", description = "Retorna uma lista de todas as solicitações cadastradas")
    public ResponseEntity<List<SolicitacaoProdutoResponse>> listar() {
        logger.info("Requisição GET /api/solicitacoes");
        List<SolicitacaoProdutoResponse> solicitacoes = service.listar();
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar solicitações por status", description = "Retorna solicitações filtradas por status")
    public ResponseEntity<List<SolicitacaoProdutoResponse>> listarPorStatus(
            @PathVariable StatusSolicitacao status) {
        logger.info("Requisição GET /api/solicitacoes/status/{}", status);
        List<SolicitacaoProdutoResponse> solicitacoes = service.listarPorStatus(status);
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/solicitante/{solicitante}")
    @Operation(summary = "Listar solicitações por solicitante", description = "Retorna solicitações filtradas por solicitante")
    public ResponseEntity<List<SolicitacaoProdutoResponse>> listarPorSolicitante(
            @PathVariable String solicitante) {
        logger.info("Requisição GET /api/solicitacoes/solicitante/{}", solicitante);
        List<SolicitacaoProdutoResponse> solicitacoes = service.listarPorSolicitante(solicitante);
        return ResponseEntity.ok(solicitacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter solicitação por ID", description = "Retorna os detalhes de uma solicitação específica")
    public ResponseEntity<SolicitacaoProdutoResponse> buscarPorId(@PathVariable Long id) {
        logger.info("Requisição GET /api/solicitacoes/{}", id);
        SolicitacaoProdutoResponse solicitacao = service.buscarPorId(id);
        return ResponseEntity.ok(solicitacao);
    }

    @PostMapping
    @Operation(summary = "Criar nova solicitação", description = "Cria uma nova solicitação de produto")
    public ResponseEntity<SolicitacaoProdutoResponse> criar(
            @Valid @RequestBody CriarSolicitacaoProdutoRequest request) {
        logger.info("Requisição POST /api/solicitacoes");
        SolicitacaoProdutoResponse solicitacao = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitacao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar solicitação", description = "Atualiza uma solicitação existente")
    public ResponseEntity<SolicitacaoProdutoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarSolicitacaoProdutoRequest request) {
        logger.info("Requisição PUT /api/solicitacoes/{}", id);
        SolicitacaoProdutoResponse solicitacao = service.atualizar(id, request);
        return ResponseEntity.ok(solicitacao);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar solicitação", description = "Deleta uma solicitação existente")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logger.info("Requisição DELETE /api/solicitacoes/{}", id);
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
