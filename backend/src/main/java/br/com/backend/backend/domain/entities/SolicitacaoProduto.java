package br.com.backend.backend.domain.entities;

import br.com.backend.backend.domain.enums.PrioridadeSolicitacao;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao_produto", indexes = {
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_data_criacao", columnList = "data_criacao"),
    @Index(name = "idx_solicitante", columnList = "solicitante")
})
public class SolicitacaoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nomeProduto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, length = 255)
    private String solicitante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadeSolicitacao prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public SolicitacaoProduto() {}

    public SolicitacaoProduto(String nomeProduto, Integer quantidade, String solicitante, 
                            PrioridadeSolicitacao prioridade, String observacao) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.solicitante = solicitante;
        this.prioridade = prioridade;
        this.status = StatusSolicitacao.ABERTA;
        this.observacao = observacao;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
        if (this.dataAtualizacao == null) {
            this.dataAtualizacao = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
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
