import { useEffect, useState } from 'react';
import './styles/index.css';
import './App.css';

import { Layout } from './components/Layout';
import { ListaSolicitacoes } from './pages/ListaSolicitacoes';
import { CadastroSolicitacao } from './pages/CadastroSolicitacao';

export default function App() {
  const [route, setRoute] = useState<string>(window.location.hash || '#/');

  useEffect(() => {
    const onHashChange = () => setRoute(window.location.hash || '#/');
    window.addEventListener('hashchange', onHashChange);
    return () => window.removeEventListener('hashchange', onHashChange);
  }, []);

  let content = null;

  const editMatch = route.match(/^#\/solicitacoes\/(\d+)\/editar$/);

  if (editMatch) {
    content = <CadastroSolicitacao id={Number(editMatch[1])} />;
  } else if (route.startsWith('#/solicitacoes/novo')) {
    content = <CadastroSolicitacao />;
  } else if (route.startsWith('#/solicitacoes')) {
    content = <ListaSolicitacoes />;
  } else {
    content = (
      <div className="welcome-section">
        <h2>Bem-vindo ao Sistema de Solicitações</h2>
        <p>Gestão de solicitações de produtos para estoque corporativo</p>
      </div>
    );
  }

  return <Layout>{content}</Layout>;
}