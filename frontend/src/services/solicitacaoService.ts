import axios from 'axios';
import type {
  SolicitacaoProduto,
  CriarSolicitacaoProdutoRequest,
  AtualizarSolicitacaoProdutoRequest,
} from '../types';
import { StatusSolicitacao } from '../types';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const solicitacaoService = {
  listar: async (): Promise<SolicitacaoProduto[]> => {
    const response = await apiClient.get<SolicitacaoProduto[]>('/solicitacoes');
    return response.data;
  },

  buscarPorId: async (id: number): Promise<SolicitacaoProduto> => {
    const response = await apiClient.get<SolicitacaoProduto>(`/solicitacoes/${id}`);
    return response.data;
  },

  listarPorStatus: async (status: StatusSolicitacao): Promise<SolicitacaoProduto[]> => {
    const response = await apiClient.get<SolicitacaoProduto[]>(`/solicitacoes/status/${status}`);
    return response.data;
  },

  listarPorSolicitante: async (solicitante: string): Promise<SolicitacaoProduto[]> => {
    const response = await apiClient.get<SolicitacaoProduto[]>(
      `/solicitacoes/solicitante/${solicitante}`
    );
    return response.data;
  },

  criar: async (request: CriarSolicitacaoProdutoRequest): Promise<SolicitacaoProduto> => {
    const response = await apiClient.post<SolicitacaoProduto>('/solicitacoes', request);
    return response.data;
  },

  atualizar: async (
    id: number,
    request: AtualizarSolicitacaoProdutoRequest
  ): Promise<SolicitacaoProduto> => {
    const response = await apiClient.put<SolicitacaoProduto>(`/solicitacoes/${id}`, request);
    return response.data;
  },

  deletar: async (id: number): Promise<void> => {
    await apiClient.delete(`/solicitacoes/${id}`);
  },
};
