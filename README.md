# Sistema de Solicitações de Produtos

## Sobre o projeto

Este projeto foi desenvolvido como solução para o desafio técnico de Desenvolvimento e Análise de Incidentes.

A proposta era implementar uma funcionalidade completa envolvendo front-end, back-end, persistência de dados, validações, testes e mecanismos mínimos de observabilidade, além de realizar a análise de um cenário de incidente e propor ações corretivas e preventivas.

A aplicação desenvolvida permite o gerenciamento de solicitações de produtos para estoque através de um fluxo simples de cadastro, consulta, atualização e remoção de solicitações.

Durante o desenvolvimento procurei organizar o projeto utilizando uma arquitetura em camadas, separando responsabilidades entre controller, service, repository e domínio, buscando manter o código de fácil manutenção e evolução.

---

## Tecnologias utilizadas

### Backend

O backend foi desenvolvido utilizando Java 21 e Spring Boot.

Principais recursos utilizados:

* Spring Web para construção da API REST;
* Spring Data JPA para persistência;
* PostgreSQL como banco de dados;
* Flyway para controle de versões do banco;
* Bean Validation para validações de entrada;
* Swagger/OpenAPI para documentação da API;
* Logback para geração de logs;
* JUnit e Mockito para testes automatizados.

### Frontend

O frontend foi desenvolvido utilizando React com TypeScript.

Principais bibliotecas:

* React 19;
* Vite;
* Axios para comunicação com a API;
* TanStack Query para gerenciamento das requisições;
* React Hook Form para construção dos formulários;
* Zod para validação dos dados no cliente.

---

## Funcionalidades implementadas

A aplicação permite:

* Cadastrar uma nova solicitação de produto;
* Consultar todas as solicitações cadastradas;
* Buscar solicitações por identificador;
* Filtrar solicitações por status;
* Atualizar informações de uma solicitação;
* Remover solicitações existentes;
* Validar regras de negócio antes da persistência;
* Registrar logs para rastreabilidade das operações.

Os status disponíveis para uma solicitação são:

ABERTA → EM_ANALISE → APROVADA ou REJEITADA

---

## Como executar o projeto

### Pré-requisitos

Antes de iniciar, é necessário possuir instalado:

* Java 21 ou superior;
* Maven 3.9 ou superior;
* Node.js 18 ou superior;
* PostgreSQL.

### Configuração do banco de dados

Criar um banco PostgreSQL com as seguintes configurações:

Banco:
backend

Usuário:
backend

Senha:
backend

Caso deseje utilizar outras credenciais, basta ajustar os arquivos de configuração da aplicação.

---

### Executando o backend

Acesse a pasta do backend:

```bash
cd backend
```

Instale as dependências e gere o build:

```bash
mvn clean install
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

Após a inicialização, a API ficará disponível em:

```text
http://localhost:8080
```

A documentação Swagger poderá ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

Ao iniciar pela primeira vez, o Flyway executará automaticamente as migrations necessárias para criação da estrutura do banco de dados.

---

### Executando o frontend

Acesse a pasta do frontend:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
```

Inicie a aplicação:

```bash
npm run dev
```

O frontend ficará disponível em:

```text
http://localhost:5173
```

---

## Executando os testes

Os principais cenários de negócio possuem cobertura através de testes unitários e de integração.

Para executar todos os testes:

```bash
cd backend
mvn clean test
```

Os testes contemplam:

* criação de solicitações;
* validações de entrada;
* atualização de registros;
* remoção de solicitações;
* tratamento de recursos inexistentes;
* regras de negócio da camada de serviço.

---

## Decisões técnicas

Algumas decisões foram tomadas buscando simplicidade, clareza e facilidade de manutenção.

O PostgreSQL foi escolhido por ser um banco relacional amplamente utilizado e possuir ótima integração com o ecossistema Spring.

O Flyway foi utilizado para garantir versionamento da estrutura do banco e permitir que qualquer pessoa consiga recriar o ambiente com segurança.

No frontend foi utilizado TanStack Query para simplificar o gerenciamento das chamadas à API e o controle de cache dos dados.

Também foi implementado um mecanismo de Request ID para rastreabilidade das requisições. Cada chamada recebe um identificador único que é propagado para os logs da aplicação, facilitando a investigação de problemas e a correlação de eventos.

---

## Análise de incidente

A segunda parte do desafio consistiu na análise de cenários de falha e definição de estratégias preventivas.

Foram avaliados cenários como:

* envio de dados inválidos;
* remoção de registros inexistentes;
* perda de rastreabilidade em produção;
* problemas de configuração de CORS;
* inconsistências de data e timezone;
* falhas de validação de campos obrigatórios;
* controle transacional em operações de persistência.

As soluções adotadas incluem validações em múltiplas camadas, tratamento centralizado de exceções, uso de logs estruturados, controle transacional e testes automatizados.

Mais detalhes podem ser encontrados no documento INCIDENT_ANALYSIS.md.

---

## Possíveis evoluções

Caso o projeto evoluísse para um ambiente produtivo, alguns pontos que poderiam ser implementados seriam:

* autenticação e autorização com JWT;
* histórico de alterações das solicitações;
* paginação das consultas;
* cache para consultas frequentes;
* monitoramento com métricas e dashboards;
* pipeline de integração e entrega contínua;
* containerização com Docker.

---

## Considerações finais

O objetivo principal deste projeto foi demonstrar a implementação de uma funcionalidade completa envolvendo backend, frontend, banco de dados, testes automatizados e análise de incidentes.

Além da entrega funcional, procurei adotar boas práticas de desenvolvimento, organização do código, tratamento de erros, observabilidade e documentação, buscando uma solução simples, mas preparada para evolução futura.
