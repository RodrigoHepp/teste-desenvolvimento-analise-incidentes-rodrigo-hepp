import type { ReactNode } from 'react';
import '../styles/Layout.css';

interface LayoutProps {
  children: ReactNode;
}

export function Layout({ children }: LayoutProps) {
  return (
    <div className="layout">
      <header className="header">
        <div className="container">
          <h1>Sistema de Solicitações de Produtos</h1>
          <p className="subtitle">Gestão de estoque corporativo</p>
          <nav className="main-nav" aria-label="Navegação principal">
            <a href="#/">Início</a>
            <a href="#/solicitacoes">Minhas Solicitações</a>
            <a href="#/solicitacoes/novo">Nova Solicitação</a>
          </nav>
        </div>
      </header>

      <main className="main-content">
        <div className="container">
          {children}
        </div>
      </main>

      <footer className="footer">
        <div className="container">
          <p>&copy; 2026 Sistema de Solicitações. Todos os direitos reservados.</p>
        </div>
      </footer>
    </div>
  );
}
