export enum StatusSolicitacao {
  ABERTA = 'ABERTA',
  EM_ANALISE = 'EM_ANALISE',
  APROVADA = 'APROVADA',
  REJEITADA = 'REJEITADA',
}

export enum PrioridadeSolicitacao {
  BAIXA = 'BAIXA',
  MEDIA = 'MEDIA',
  ALTA = 'ALTA',
  CRITICA = 'CRITICA',
}

export interface SolicitacaoProduto {
  id: number;
  nomeProduto: string;
  quantidade: number;
  solicitante: string;
  prioridade: PrioridadeSolicitacao;
  status: StatusSolicitacao;
  observacao?: string;
  dataCriacao: string;
  dataAtualizacao: string;
}

export interface CriarSolicitacaoProdutoRequest {
  nomeProduto: string;
  quantidade: number;
  solicitante: string;
  prioridade: PrioridadeSolicitacao;
  observacao?: string;
}

export interface AtualizarSolicitacaoProdutoRequest {
  nomeProduto: string;
  quantidade: number;
  solicitante: string;
  prioridade: PrioridadeSolicitacao;
  status: StatusSolicitacao;
  observacao?: string;
}
