import { useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { solicitacaoService } from '../services/solicitacaoService'
import { Layout } from '../components/Layout'
import { Button } from '../components/Button'
import '../styles/ListaSolicitacoes.css'

export function ListaSolicitacoes() {
  const [filtroStatus, setFiltroStatus] = useState<string>('')
  
  const { data: solicitacoes = [], isLoading } = useQuery({
    queryKey: ['solicitacoes'],
    queryFn: () => solicitacaoService.listar(),
  })

  const filtradas = filtroStatus
    ? solicitacoes.filter((s) => s.status === filtroStatus)
    : solicitacoes

  return (
    <Layout>
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
          <Button variant="primary">+ Nova Solicitação</Button>
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
                  <Button variant="secondary" onClick={() => {}}>Editar</Button>
                  <Button variant="danger" onClick={() => {}}>Deletar</Button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </Layout>
  )
}
