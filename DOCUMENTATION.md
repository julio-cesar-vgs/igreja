# Documentação Técnica: Gestão de Igrejas (DDD & Event-Driven)

Este documento descreve a nova arquitetura do sistema, baseada em **Domain-Driven Design (DDD)** e **Event-Driven Architecture**, focando em modularidade, escalabilidade e clareza de negócio.

## 💾 Banco de Dados: Relacional vs NoSQL

Uma dúvida comum neste projeto foi a escolha entre banco de dados Relacional ou NoSQL. A arquitetura atual utiliza uma abordagem **Híbrida (Persistence Polyglot)**:

1.  **MySQL (Relacional)**: Utilizado como **Fonte da Verdade**.
    - **Por que?** Dados financeiros (Dízimos, Ofertas) e cadastrais exigem consistência **ACID**. As relações complexas entre Igrejas, Cultos e seus itens são tratadas de forma nativa e eficiente por bancos relacionais.
    - **Isolamento**: Implementamos Multi-tenancy nativo via Hibernate para garantir que cada igreja acesse apenas seus dados.
    - **Timezone**: Configurado para `America/Sao_Paulo`.
2.  **Redis (NoSQL Key-Value)**: Utilizado para **Cache e Real-time**.
    - Agiliza a leitura de dashboards consolidados e estados temporários do culto.
3.  **Kafka (NoSQL Log-based)**: Utilizado para **Mensageria e Eventos**.
    - Garante que o sistema seja **Event-Driven**, permitindo que atualizações de um contexto (ex: novo dízimo) notifiquem outros contextos ou o frontend em tempo real.

---

## 🏗️ Arquitetura DDD (Domain-Driven Design)

O projeto foi reorganizado em **Bounded Contexts** (Contextos Delimitados), garantindo que cada módulo tenha sua própria lógica e responsabilidades bem definidas.

### Estrutura Modular
A estrutura de pacotes agora segue um padrão vertical por módulo:
`br.com.igreja.ipiranga.modules.[modulo].[camada]`

#### Módulos Implementados:
1.  **Identity (Identidade)**: Gestão de usuários, roles e autenticação JWT.
2.  **Igreja (Cadastro)**: Configurações e dados das unidades (Matriz/Filial).
3.  **Culto (Liturgia)**: Gestão do evento de culto, louvores, participantes e dashboard.
4.  **Financeiro (Tesouraria)**: Gestão de dízimos, ofertas e conferência.
5.  **Audit (Auditoria)**: Registro automático de trilhas de auditoria via AOP.
6.  **Shared (Compartilhado)**: Elementos transversais como `TenantEntity` e `DomainEvent`.

### Camadas por Módulo:
- **Domain**: Contém as Entidades, Agregados, Eventos de Domínio e Interfaces de Repositório. É o coração do negócio, livre de dependências de infraestrutura externa.
- **Application**: Contém os Services de Aplicação que orquestram o fluxo de dados, disparam eventos de integração e realizam conversões de DTOs.
- **Infrastructure**: Implementações técnicas (Persistência, Segurança, Integrações Kafka).
- **Web**: Controllers REST que expõem as funcionalidades.

---

## 📡 Event-Driven Architecture

O sistema utiliza eventos para comunicação desacoplada:

1.  **Domain Events**: Eventos que ocorrem dentro do domínio (ex: `CultoIniciado`). Atualmente representados pela classe base `DomainEvent`.
2.  **Integration Events (Kafka)**: Mensagens enviadas para o tópico `culto-updates` sempre que uma alteração relevante ocorre.
3.  **Real-time (WebSockets)**: O `CultoEventListener` captura eventos do Kafka e os transmite via WebSocket para o frontend, permitindo atualizações instantâneas no dashboard sem necessidade de refresh.

---

## 📖 Documentação da API (Swagger/OpenAPI)

Para facilitar o desenvolvimento e teste dos endpoints, o projeto conta com o **Swagger UI**.

- **URL Local**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Autenticação no Swagger
1. Realize o login via endpoint `/auth/login` ou registre-se via `/auth/register`.
2. Copie o `token` gerado.
3. No Swagger UI, clique no botão **Authorize** (cadeado) e cole o token no formato `Bearer <seu_token>`.

### Novos Endpoints de Autenticação
- `POST /auth/register`: Registra um novo usuário vinculado a uma igreja. Requer nome, email, senha, igrejaId e role.
- `POST /auth/login`: Autentica o usuário e retorna o token JWT com as claims de acesso e tenant.

---

## 🛠️ Como Executar

O projeto continua totalmente conteinerizado:

```bash
docker-compose up --build
```

Isso subirá:
- **Backend**: Spring Boot 3.4.1 (Java 21).
- **Database**: MySQL 8.0 (Porta 3306).
- **Cache**: Redis.
- **Messaging**: Kafka + Zookeeper.

---
*Esta reestruturação visa facilitar a manutenção e a evolução do sistema para um ambiente de microserviços no futuro, se necessário.*
