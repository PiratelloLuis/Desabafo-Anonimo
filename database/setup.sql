-- Script para criar o banco de dados PostgreSQL
-- Execute este script antes de rodar a aplicação

-- Criar o banco de dados
CREATE DATABASE desabafo_db;

-- Conectar ao banco
\c desabafo_db;

-- As tabelas serão criadas automaticamente pelo Hibernate
-- mas você pode criar manualmente se preferir:

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    ip_hash VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de mensagens
CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    content VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Índices para melhorar performance
CREATE INDEX idx_messages_user_id ON messages(user_id);
CREATE INDEX idx_messages_created_at ON messages(created_at DESC);
CREATE INDEX idx_users_ip_hash ON users(ip_hash);

-- Visualizar estrutura
\dt
