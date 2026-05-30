import '../styles/Layout.css';

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="layout">
      <header className="header">
        <div className="container">
          <h1>Sistema de Solicitações de Produtos</h1>
          <p className="subtitle">Gestão de estoque corporativo</p>
        </div>
      </header>

      <main className="main-content">
        <div className="container">{children}</div>
      </main>

      <footer className="footer">
        <p>&copy; 2026 Sistema de Solicitações. Todos os direitos reservados.</p>
      </footer>
    </div>
  );
}
