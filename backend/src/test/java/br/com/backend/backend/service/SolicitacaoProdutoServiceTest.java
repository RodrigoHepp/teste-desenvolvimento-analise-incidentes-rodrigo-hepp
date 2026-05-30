package br.com.backend.backend.service;

import br.com.backend.backend.domain.entities.SolicitacaoProduto;
import br.com.backend.backend.domain.enums.PrioridadeSolicitacao;
import br.com.backend.backend.domain.enums.StatusSolicitacao;
import br.com.backend.backend.dto.request.AtualizarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.request.CriarSolicitacaoProdutoRequest;
import br.com.backend.backend.dto.response.SolicitacaoProdutoResponse;
import br.com.backend.backend.exception.RecursoNaoEncontradoException;
import br.com.backend.backend.repository.SolicitacaoProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SolicitacaoProdutoService Tests")
class SolicitacaoProdutoServiceTest {

    @Mock
    private SolicitacaoProdutoRepository repository;

    @InjectMocks
    private SolicitacaoProdutoService service;

    private CriarSolicitacaoProdutoRequest criarRequest;
    private AtualizarSolicitacaoProdutoRequest atualizarRequest;
    private SolicitacaoProduto solicitacao;

    @BeforeEach
    void setup() {
        criarRequest = new CriarSolicitacaoProdutoRequest(
                "Notebook",
                5,
                "João Silva",
                PrioridadeSolicitacao.ALTA,
                "Urgente para projeto"
        );

        atualizarRequest = new AtualizarSolicitacaoProdutoRequest(
                "Monitor",
                10,
                "Maria Santos",
                PrioridadeSolicitacao.MEDIA,
                StatusSolicitacao.EM_ANALISE,
                "Atualização pendente"
        );

        solicitacao = new SolicitacaoProduto(
                "Notebook",
                5,
                "João Silva",
                PrioridadeSolicitacao.ALTA,
                "Urgente para projeto"
        );
        solicitacao.setId(1L);
        solicitacao.setDataCriacao(LocalDateTime.now());
        solicitacao.setDataAtualizacao(LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve criar uma nova solicitação com sucesso")
    void testCriarSolicitacao() {
        when(repository.save(any(SolicitacaoProduto.class))).thenReturn(solicitacao);

        SolicitacaoProdutoResponse response = service.criar(criarRequest);

        assertNotNull(response);
        assertEquals("Notebook", response.getNomeProduto());
        assertEquals(5, response.getQuantidade());
        assertEquals("João Silva", response.getSolicitante());
        assertEquals(StatusSolicitacao.ABERTA, response.getStatus());

        verify(repository, times(1)).save(any(SolicitacaoProduto.class));
    }

    @Test
    @DisplayName("Deve buscar solicitação por ID com sucesso")
    void testBuscarPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitacao));

        SolicitacaoProdutoResponse response = service.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("Notebook", response.getNomeProduto());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar solicitação inexistente")
    void testBuscarPorIdNaoEncontrado() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscarPorId(999L));
        verify(repository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve listar todas as solicitações")
    void testListarSolicitacoes() {
        SolicitacaoProduto solicitacao2 = new SolicitacaoProduto("Monitor", 3, "Maria", PrioridadeSolicitacao.BAIXA, "");
        solicitacao2.setId(2L);

        when(repository.findAllByOrderByDataCriacaoDesc()).thenReturn(List.of(solicitacao, solicitacao2));

        List<SolicitacaoProdutoResponse> responses = service.listar();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(repository, times(1)).findAllByOrderByDataCriacaoDesc();
    }

    @Test
    @DisplayName("Deve listar solicitações por status")
    void testListarPorStatus() {
        when(repository.findByStatusOrderByDataCriacaoDesc(StatusSolicitacao.ABERTA))
                .thenReturn(List.of(solicitacao));

        List<SolicitacaoProdutoResponse> responses = service.listarPorStatus(StatusSolicitacao.ABERTA);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(repository, times(1)).findByStatusOrderByDataCriacaoDesc(StatusSolicitacao.ABERTA);
    }

    @Test
    @DisplayName("Deve atualizar solicitação com sucesso")
    void testAtualizarSolicitacao() {
        when(repository.findById(1L)).thenReturn(Optional.of(solicitacao));
        when(repository.save(any(SolicitacaoProduto.class))).thenReturn(solicitacao);

        SolicitacaoProdutoResponse response = service.atualizar(1L, atualizarRequest);

        assertNotNull(response);
        assertEquals("Notebook", response.getNomeProduto());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(SolicitacaoProduto.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar solicitação inexistente")
    void testAtualizarSolicitacaoNaoEncontrada() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.atualizar(999L, atualizarRequest));
        verify(repository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Deve deletar solicitação com sucesso")
    void testDeletarSolicitacao() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar solicitação inexistente")
    void testDeletarSolicitacaoNaoEncontrada() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThrows(RecursoNaoEncontradoException.class, () -> service.deletar(999L));
        verify(repository, times(1)).existsById(999L);
    }
}
