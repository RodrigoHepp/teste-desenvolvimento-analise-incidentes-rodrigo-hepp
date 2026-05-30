package br.com.backend.backend.dto.response;

import br.com.backend.backend.domain.entities.SolicitacaoProduto;
import br.com.backend.backend.domain.enums.PrioridadeSolicitacao;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class SolicitacaoProdutoResponse {

    private Long id;
    private String nomeProduto;
    private Integer quantidade;
    private String solicitante;
    private PrioridadeSolicitacao prioridade;
    private StatusSolicitacao status;
    private String observacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    public SolicitacaoProdutoResponse() {}

    public SolicitacaoProdutoResponse(SolicitacaoProduto solicitacao) {
        this.id = solicitacao.getId();
        this.nomeProduto = solicitacao.getNomeProduto();
        this.quantidade = solicitacao.getQuantidade();
        this.solicitante = solicitacao.getSolicitante();
        this.prioridade = solicitacao.getPrioridade();
        this.status = solicitacao.getStatus();
        this.observacao = solicitacao.getObservacao();
        this.dataCriacao = solicitacao.getDataCriacao();
        this.dataAtualizacao = solicitacao.getDataAtualizacao();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
