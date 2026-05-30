package br.com.backend.backend.exception;

import java.time.LocalDateTime;
import java.util.List;

public class StandardErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String mensagem;
    private String caminho;
    private List<String> erros;

    public StandardErrorResponse(LocalDateTime timestamp, int status, String mensagem, String caminho) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }

    public StandardErrorResponse(LocalDateTime timestamp, int status, String mensagem, String caminho, List<String> erros) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.erros = erros;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }
}
