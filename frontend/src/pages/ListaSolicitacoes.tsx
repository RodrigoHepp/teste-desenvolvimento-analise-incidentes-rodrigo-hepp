import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { solicitacaoService } from '../services/solicitacaoService'
import { Button } from '../components/Button'
import '../styles/ListaSolicitacoes.css'

export function ListaSolicitacoes() {
  const [filtroStatus, setFiltroStatus] = useState<string>('')
  const queryClient = useQueryClient()

  const { data: solicitacoes = [], isLoading } = useQuery({
    queryKey: ['solicitacoes'],
    queryFn: () => solicitacaoService.listar(),
  })

  const deletarMutation = useMutation({
    mutationFn: (id: number) => solicitacaoService.deletar(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['solicitacoes'] })
    },
    onError: () => {
      alert('Erro ao excluir a solicitação. Tente novamente.')
    },
  })

  const handleEditar = (id: number) => {
    window.location.hash = `#/solicitacoes/${id}/editar`
  }

  const handleDeletar = (id: number, nomeProduto: string) => {
    if (confirm(`Deseja realmente excluir a solicitação de "${nomeProduto}"?`)) {
      deletarMutation.mutate(id)
    }
  }

  const filtradas = filtroStatus
    ? solicitacoes.filter((s) => s.status === filtroStatus)
    : solicitacoes

  return (
    <div className="lista-container">
      <h2>Minhas Solicitações</h2>

      <div className="lista-header">
        <select
          value={filtroStatus}
          onChange={(e) => setFiltroStatus(e.target.value)}
          className="filtro-select"
        >
          <option value="">Todos os status</option>
          <option value="ABERTA">Aberta</option>
          <option value="EM_ANALISE">Em Análise</option>
          <option value="APROVADA">Aprovada</option>
          <option value="REJEITADA">Rejeitada</option>
        </select>
        <Button variant="primary" onClick={() => (window.location.hash = '#/solicitacoes/novo')}>
          + Nova Solicitação
        </Button>
      </div>

      {isLoading && <p>Carregando...</p>}

      {filtradas.length === 0 ? (
        <p className="vazio">Nenhuma solicitação encontrada</p>
      ) : (
        <div className="solicitacoes-grid">
          {filtradas.map((sol) => (
            <div key={sol.id} className="solicitacao-card">
              <div className="card-header">
                <h3>{sol.nomeProduto}</h3>
                <span className={`badge status-${sol.status.toLowerCase()}`}>
                  {sol.status}
                </span>
              </div>
              <p><strong>Quantidade:</strong> {sol.quantidade}</p>
              <p><strong>Solicitante:</strong> {sol.solicitante}</p>
              <p><strong>Prioridade:</strong> {sol.prioridade}</p>
              <div className="card-actions">
                <Button
                  variant="secondary"
                  onClick={() => handleEditar(sol.id)}
                >
                  Editar
                </Button>
                <Button
                  variant="danger"
                  onClick={() => handleDeletar(sol.id, sol.nomeProduto)}
                  disabled={deletarMutation.isPending}
                >
                  {deletarMutation.isPending ? 'Excluindo...' : 'Deletar'}
                </Button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}