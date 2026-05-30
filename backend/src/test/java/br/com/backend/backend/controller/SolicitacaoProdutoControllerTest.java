package br.com.backend.backend.controller;

import br.com.backend.backend.domain.enums.PrioridadeSolicitacao;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import br.com.backend.backend.dto.request.CriarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.response.SolicitacaoProdutoResponse;
import br.com.backend.backend.service.SolicitacaoProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolicitacaoProdutoController.class)
@DisplayName("SolicitacaoProdutoController Tests")
class SolicitacaoProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

   
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private SolicitacaoProdutoService service;

    private SolicitacaoProdutoResponse solicitacaoResponse;
    private CriarSolicitacaoProdutoRequest criarRequest;

    @BeforeEach
    void setup() {
        LocalDateTime agora = LocalDateTime.of(2026, 5, 30, 12, 0);

        solicitacaoResponse = new SolicitacaoProdutoResponse();
        solicitacaoResponse.setId(1L);
        solicitacaoResponse.setNomeProduto("Notebook");
        solicitacaoResponse.setQuantidade(5);
        solicitacaoResponse.setSolicitante("João Silva");
        solicitacaoResponse.setPrioridade(PrioridadeSolicitacao.ALTA);
        solicitacaoResponse.setStatus(StatusSolicitacao.ABERTA);
        solicitacaoResponse.setObservacao("Urgente");
        solicitacaoResponse.setDataCriacao(agora);
        solicitacaoResponse.setDataAtualizacao(agora);

        criarRequest = new CriarSolicitacaoProdutoRequest(
                "Notebook",
                5,
                "João Silva",
                PrioridadeSolicitacao.ALTA,
                "Urgente"
        );
    }

    @Test
    @DisplayName("GET /api/solicitacoes - Deve retornar lista de solicitações")
    void testListarSolicitacoes() throws Exception {
        when(service.listar()).thenReturn(List.of(solicitacaoResponse));

        mockMvc.perform(get("/api/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomeProduto").value("Notebook"));
    }

    @Test
    @DisplayName("GET /api/solicitacoes/{id} - Deve retornar solicitação por ID")
    void testBuscarSolicitacaoPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(solicitacaoResponse);

        mockMvc.perform(get("/api/solicitacoes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeProduto").value("Notebook"))
                .andExpect(jsonPath("$.solicitante").value("João Silva"));
    }

    @Test
    @DisplayName("POST /api/solicitacoes - Deve criar nova solicitação")
    void testCriarSolicitacao() throws Exception {
        when(service.criar(any())).thenReturn(solicitacaoResponse);

        mockMvc.perform(post("/api/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criarRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeProduto").value("Notebook"))
                .andExpect(jsonPath("$.status").value("ABERTA"));
    }

    @Test
    @DisplayName("PUT /api/solicitacoes/{id} - Deve atualizar solicitação")
    void testAtualizarSolicitacao() throws Exception {
        when(service.atualizar(eq(1L), any())).thenReturn(solicitacaoResponse);

        String updateJson = """
                {
                  "nomeProduto":"Monitor",
                  "quantidade":10,
                  "solicitante":"Maria",
                  "prioridade":"MEDIA",
                  "status":"EM_ANALISE",
                  "observacao":"Atualizado"
                }
                """;

        mockMvc.perform(put("/api/solicitacoes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/solicitacoes/{id} - Deve deletar solicitação")
    void testDeletarSolicitacao() throws Exception {
        mockMvc.perform(delete("/api/solicitacoes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/solicitacoes/status/{status} - Deve listar por status")
    void testListarPorStatus() throws Exception {
        when(service.listarPorStatus(StatusSolicitacao.ABERTA))
                .thenReturn(List.of(solicitacaoResponse));

        mockMvc.perform(get("/api/solicitacoes/status/ABERTA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("ABERTA"));
    }

    @Test
    @DisplayName("POST /api/solicitacoes - Deve retornar 400 para request inválida")
    void testCriarSolicitacaoInvalida() throws Exception {
        String invalidRequest = """
                {
                  "nomeProduto":"",
                  "quantidade":-5
                }
                """;

        mockMvc.perform(post("/api/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }
}