import { useState, useEffect } from 'react'
import { solicitacaoService } from '../services/solicitacaoService'
import { PrioridadeSolicitacao, StatusSolicitacao } from '../types'
import { Button } from '../components/Button'

interface Props {
  id?: number
}

export function CadastroSolicitacao({ id }: Props) {
  const isEdicao = !!id

  const [nomeProduto, setNomeProduto] = useState('')
  const [quantidade, setQuantidade] = useState<number>(1)
  const [solicitante, setSolicitante] = useState('')
  const [prioridade, setPrioridade] = useState<PrioridadeSolicitacao>(PrioridadeSolicitacao.MEDIA)
  const [status, setStatus] = useState<StatusSolicitacao>(StatusSolicitacao.ABERTA)
  const [observacao, setObservacao] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [isLoading, setIsLoading] = useState(isEdicao)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    if (!id) return

    solicitacaoService.buscarPorId(id)
      .then((sol) => {
        setNomeProduto(sol.nomeProduto)
        setQuantidade(sol.quantidade)
        setSolicitante(sol.solicitante)
        setPrioridade(sol.prioridade)
        setStatus(sol.status)
        setObservacao(sol.observacao ?? '')
      })
      .catch(() => setError('Não foi possível carregar a solicitação'))
      .finally(() => setIsLoading(false))
  }, [id])

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)

    const nomeTrim = nomeProduto.trim()
    const solicitanteTrim = solicitante.trim()

    if (nomeTrim.length < 3) {
      setError('Nome do produto deve ter ao menos 3 caracteres')
      return
    }
    if (solicitanteTrim.length < 3 || solicitanteTrim.length > 255) {
      setError('Solicitante deve ter entre 3 e 255 caracteres')
      return
    }
    if (quantidade < 1) {
      setError('Quantidade deve ser ao menos 1')
      return
    }

    setIsSubmitting(true)

    try {
      if (isEdicao) {
        await solicitacaoService.atualizar(id, {
          nomeProduto: nomeTrim,
          quantidade,
          solicitante: solicitanteTrim,
          prioridade,
          status,
          observacao: observacao || undefined,
        })
      } else {
        await solicitacaoService.criar({
          nomeProduto: nomeTrim,
          quantidade,
          solicitante: solicitanteTrim,
          prioridade,
          observacao: observacao || undefined,
        })
      }

      window.location.hash = '#/solicitacoes'
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Erro ao salvar solicitação')
    } finally {
      setIsSubmitting(false)
    }
  }

  if (isLoading) return <p>Carregando...</p>

  return (
    <div className="cadastro-container">
      <h2>{isEdicao ? 'Editar Solicitação' : 'Nova Solicitação'}</h2>
      <form onSubmit={onSubmit} className="cadastro-form">
        <div className="form-group">
          <label>Nome do Produto</label>
          <input value={nomeProduto} onChange={(e) => setNomeProduto(e.target.value)} required />
        </div>

        <div className="form-group">
          <label>Quantidade</label>
          <input type="number" value={quantidade} min={1} onChange={(e) => setQuantidade(Number(e.target.value))} required />
        </div>

        <div className="form-group">
          <label>Solicitante</label>
          <input value={solicitante} minLength={3} onChange={(e) => setSolicitante(e.target.value)} required placeholder="Nome do solicitante (mínimo 3 caracteres)" />
        </div>

        <div className="form-group">
          <label>Prioridade</label>
          <select value={prioridade} onChange={(e) => setPrioridade(e.target.value as PrioridadeSolicitacao)}>
            <option value={PrioridadeSolicitacao.BAIXA}>Baixa</option>
            <option value={PrioridadeSolicitacao.MEDIA}>Média</option>
            <option value={PrioridadeSolicitacao.ALTA}>Alta</option>
            <option value={PrioridadeSolicitacao.CRITICA}>Crítica</option>
          </select>
        </div>

        {/* Campo de status aparece apenas na edição */}
        {isEdicao && (
          <div className="form-group">
            <label>Status</label>
            <select value={status} onChange={(e) => setStatus(e.target.value as StatusSolicitacao)}>
              <option value={StatusSolicitacao.ABERTA}>Aberta</option>
              <option value={StatusSolicitacao.EM_ANALISE}>Em Análise</option>
              <option value={StatusSolicitacao.APROVADA}>Aprovada</option>
              <option value={StatusSolicitacao.REJEITADA}>Rejeitada</option>
            </select>
          </div>
        )}

        <div className="form-group">
          <label>Observação</label>
          <textarea value={observacao} onChange={(e) => setObservacao(e.target.value)} />
        </div>

        {error && <p className="form-error">{error}</p>}

        <div style={{ display: 'flex', gap: 8 }}>
          <Button type="submit" variant="primary" disabled={isSubmitting}>
            {isSubmitting ? 'Salvando...' : 'Salvar'}
          </Button>
          <Button type="button" variant="secondary" onClick={() => (window.location.hash = '#/solicitacoes')}>
            Cancelar
          </Button>
        </div>
      </form>
    </div>
  )
}