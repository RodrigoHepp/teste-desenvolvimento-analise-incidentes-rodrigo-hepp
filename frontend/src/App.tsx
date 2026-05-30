import './styles/index.css'
import './App.css'
import { Layout } from './components/Layout'

function App() {
  return (
    <Layout>
      <div className="welcome-section">
        <h2>Bem-vindo ao Sistema de Solicitações</h2>
        <p>Selecione uma opção no menu para começar</p>
      </div>
    </Layout>
  )
}

export default App
