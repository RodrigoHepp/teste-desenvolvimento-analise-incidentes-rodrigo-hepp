# Análise de Incidentes

Como parte do desafio, foi realizada uma análise de cenários que poderiam gerar falhas ou comportamentos inesperados na aplicação.

## Cenário 1 - Dados inválidos enviados para a API

Um dos principais riscos é o envio de informações inconsistentes, como quantidade negativa ou campos obrigatórios vazios.

### Solução adotada

- Validações utilizando Bean Validation.
- Respostas padronizadas para erros de validação.
- Restrições adicionais no banco de dados.

---

## Cenário 2 - Atualização ou exclusão de registros inexistentes

Outra situação comum é a tentativa de manipular um registro que não existe.

### Solução adotada

- Verificação da existência do recurso antes da operação.
- Retorno HTTP 404 quando o registro não for encontrado.
- Tratamento centralizado de exceções.

---

## Cenário 3 - Dificuldade para identificar problemas em produção

Sem mecanismos de rastreabilidade, a análise de erros pode se tornar difícil.

### Solução adotada

- Implementação de Request ID para cada requisição.
- Registro do identificador nos logs.
- Correlação entre chamadas e mensagens de erro.

---

## Cenário 4 - Falhas durante operações de persistência

Problemas durante gravações podem causar inconsistência nos dados.

### Solução adotada

- Utilização de controle transacional.
- Rollback automático em caso de falha.

---

## Medidas preventivas

Para reduzir a ocorrência de incidentes, foram adotadas as seguintes práticas:

- Validação de entrada de dados.
- Tratamento centralizado de exceções.
- Logs estruturados.
- Testes automatizados.
- Controle transacional.

Essas medidas contribuem para tornar a aplicação mais confiável e facilitar a identificação de problemas futuros.