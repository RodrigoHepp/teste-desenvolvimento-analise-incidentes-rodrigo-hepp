# Documentação da API

A API foi desenvolvida utilizando Spring Boot e segue o padrão REST.

Após iniciar a aplicação, a documentação interativa poderá ser acessada através do Swagger:

```text
http://localhost:8080/swagger-ui.html
```

---

## Criar solicitação

```http
POST /api/solicitacoes
```

### Exemplo de requisição

```json
{
  "nomeProduto": "Notebook",
  "quantidade": 5,
  "solicitante": "João Silva",
  "prioridade": "ALTA",
  "observacao": "Equipamento para novo colaborador"
}
```

### Retorno

```http
201 Created
```

---

## Listar solicitações

```http
GET /api/solicitacoes
```

### Retorno

```http
200 OK
```

---

## Buscar solicitação por ID

```http
GET /api/solicitacoes/{id}
```

### Retorno

```http
200 OK
```

ou

```http
404 Not Found
```

---

## Atualizar solicitação

```http
PUT /api/solicitacoes/{id}
```

### Retorno

```http
200 OK
```

---

## Excluir solicitação

```http
DELETE /api/solicitacoes/{id}
```

### Retorno

```http
204 No Content
```

---

## Buscar solicitações por status

```http
GET /api/solicitacoes/status/{status}
```

Status disponíveis:

- ABERTA
- EM_ANALISE
- APROVADA
- REJEITADA

---

## Validações

As principais validações implementadas são:

- Nome do produto obrigatório.
- Quantidade maior que zero.
- Prioridade obrigatória.
- Status válido para atualização.

Quando alguma validação falha, a API retorna:

```http
400 Bad Request
```

com a descrição do erro encontrado.