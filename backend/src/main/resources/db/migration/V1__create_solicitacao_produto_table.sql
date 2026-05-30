CREATE TABLE IF NOT EXISTS solicitacao_produto (
    id BIGSERIAL PRIMARY KEY,
    nome_produto VARCHAR(255) NOT NULL,
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    solicitante VARCHAR(255) NOT NULL,
    prioridade VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    observacao TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_status ON solicitacao_produto(status);
CREATE INDEX idx_data_criacao ON solicitacao_produto(data_criacao);
CREATE INDEX idx_solicitante ON solicitacao_produto(solicitante);
