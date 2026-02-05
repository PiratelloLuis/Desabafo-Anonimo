# 🗣️ Desabafo Anônimo

Sistema web anônimo para compartilhar desabafos. Os usuários podem enviar seus desabafos ou ler desabafos aleatórios de outras pessoas de forma completamente anônima.

## 🎯 Funcionalidades

- ✍️ **Enviar Desabafos**: Compartilhe seus sentimentos anonimamente (máx. 500 caracteres)
- 👂 **Ler Desabafos**: Receba desabafos aleatórios de outros usuários
- 📋 **Meus Desabafos**: Visualize seus próprios desabafos (identificados por IP)
- 🔒 **Privacidade**: IP hashado com SHA-256, sem armazenamento de dados pessoais
- 📊 **Estatísticas**: Veja quantos desabafos já foram compartilhados

## 🛠️ Tecnologias

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **PostgreSQL**
- **Maven**

### Frontend
- **React 18**
- **Axios**
- **CSS3**

## 📋 Pré-requisitos

- Java 17 ou superior
- Node.js 16 ou superior
- PostgreSQL 13 ou superior
- Maven 3.6 ou superior

## 🚀 Como Executar

### 1. Configurar o Banco de Dados

```bash
# Entrar no PostgreSQL
sudo -u postgres psql

# Criar o banco de dados
CREATE DATABASE desabafo_db;

# Ou executar o script SQL
\i database/setup.sql
```

### 2. Configurar o Backend

```bash
# Navegar para a pasta do backend
cd backend

# Editar as credenciais do banco (se necessário)
# Arquivo: src/main/resources/application.properties
# Altere: spring.datasource.username e spring.datasource.password

# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

O backend estará rodando em: `http://localhost:8080`

### 3. Configurar o Frontend

```bash
# Navegar para a pasta do frontend
cd frontend

# Instalar dependências
npm install

# Executar a aplicação
npm start
```

O frontend estará rodando em: `http://localhost:3000`

## 📡 API Endpoints

### POST /api/messages
Enviar um novo desabafo

**Request Body:**
```json
{
  "content": "Seu desabafo aqui..."
}
```

**Response:**
```json
{
  "id": 1,
  "content": "Seu desabafo aqui...",
  "createdAt": "2024-02-04T10:30:00"
}
```

### GET /api/messages/random
Obter um desabafo aleatório

**Response:**
```json
{
  "id": 5,
  "content": "Desabafo aleatório...",
  "createdAt": "2024-02-03T15:20:00"
}
```

### GET /api/messages/my
Obter meus desabafos (baseado no IP)

**Response:**
```json
[
  {
    "id": 1,
    "content": "Meu desabafo 1...",
    "createdAt": "2024-02-04T10:30:00"
  },
  {
    "id": 3,
    "content": "Meu desabafo 2...",
    "createdAt": "2024-02-04T14:15:00"
  }
]
```

### GET /api/messages/stats
Obter estatísticas do sistema

**Response:**
```json
{
  "totalMessages": 142
}
```

## 🔐 Privacidade e Segurança

- **IPs hashados**: Os IPs dos usuários são convertidos em hash SHA-256 antes de serem armazenados
- **Anonimato**: Não são coletados dados pessoais além do IP (hashado)
- **Identificação temporária**: O usuário é identificado apenas pelo IP durante a sessão
- **Sem login**: Sistema completamente anônimo, sem necessidade de cadastro

## 📁 Estrutura do Projeto

```
desabafo-anonimo/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/desabafo/desabafoatonimo/
│   │       │   ├── config/          # Configurações (CORS)
│   │       │   ├── controller/      # Controllers REST
│   │       │   ├── dto/             # DTOs (Request/Response)
│   │       │   ├── model/           # Entidades JPA
│   │       │   ├── repository/      # Repositories
│   │       │   ├── service/         # Lógica de negócio
│   │       │   └── util/            # Utilitários (Hash IP)
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── services/       # API service
│   │   ├── App.js          # Componente principal
│   │   ├── App.css         # Estilos
│   │   └── index.js        # Entry point
│   └── package.json
└── database/
    └── setup.sql           # Script de setup do banco
```

## 🎨 Screenshots

### Tela Inicial
Interface limpa com opções de enviar ou receber desabafos.

### Enviar Desabafo
Formulário simples com contador de caracteres (máx. 500).

### Ler Desabafo
Visualização de desabafos aleatórios de outros usuários.

## 🔄 Próximas Funcionalidades (v2.0)

- 🎤 **Áudio**: Implementar gravação e reprodução de áudios
- 🏷️ **Categorias**: Adicionar categorias aos desabafos
- ❤️ **Reações**: Permitir reações anônimas aos desabafos
- 🎯 **Filtros**: Filtrar desabafos por data/categoria
- 📊 **Dashboard**: Estatísticas mais detalhadas

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT.

## 👨‍💻 Autor

Desenvolvido com 💜 durante uma sessão de vibecodando!

---

**Nota**: Este é um projeto em desenvolvimento. A versão atual trabalha com texto, e a funcionalidade de áudio será implementada em breve.
