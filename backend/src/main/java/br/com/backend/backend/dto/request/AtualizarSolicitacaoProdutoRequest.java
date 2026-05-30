package br.com.backend.backend.dto.request;

import br.com.backend.backend.domain.enums.PrioridadeSolicitacao;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import jakarta.validation.constraints.*;

public class AtualizarSolicitacaoProdutoRequest {

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 3, max = 255, message = "Nome do produto deve ter entre 3 e 255 caracteres")
    private String nomeProduto;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotBlank(message = "Solicitante é obrigatório")
    @Size(min = 3, max = 255, message = "Solicitante deve ter entre 3 e 255 caracteres")
    private String solicitante;

    @NotNull(message = "Prioridade é obrigatória")
    private PrioridadeSolicitacao prioridade;

    @NotNull(message = "Status é obrigatório")
    private StatusSolicitacao status;

    @Size(max = 2000, message = "Observação deve ter no máximo 2000 caracteres")
    private String observacao;

    public AtualizarSolicitacaoProdutoRequest() {}

    public AtualizarSolicitacaoProdutoRequest(String nomeProduto, Integer quantidade, String solicitante,
                                              PrioridadeSolicitacao prioridade, StatusSolicitacao status, 
                                              String observacao) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.solicitante = solicitante;
        this.prioridade = prioridade;
        this.status = status;
        this.observacao = observacao;
    }

    // Getters and Setters
    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public PrioridadeSolicitacao getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeSolicitacao prioridade) {
        this.prioridade = prioridade;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
