# Diretrizes do Projeto: Gestão de Igrejas (Ipiranga)

Este documento serve como a "memória" e o guia de estilo para o desenvolvimento do sistema de gestão de igrejas. Siga estas diretrizes em todas as interações.

## 🚀 Stack Tecnológica
- **Linguagem**: Java 21
- **Framework**: Spring Boot 3.4.1
- **Banco de Dados**: MySQL 8.0 (Fonte da Verdade)
- **Cache**: Redis
- **Mensageria**: Kafka + Zookeeper
- **Segurança**: Spring Security + JWT
- **Documentação**: SpringDoc OpenAPI (Swagger)
- **Infraestrutura**: Docker & Docker Compose
- **Timezone**: America/Sao_Paulo

## 🏗️ Arquitetura e Padrões
O projeto segue os princípios de **Domain-Driven Design (DDD)** e **Event-Driven Architecture**.

### Estrutura de Módulos (Bounded Contexts)
Os módulos estão localizados em `br.com.igreja.ipiranga.modules.[modulo]`.
Atualmente implementados:
- `identity`: Gestão de usuários e autenticação.
- `igreja`: Cadastro de unidades (Matriz/Filial).
- `culto`: Gestão de liturgia e dashboards.
- `financeiro`: Tesouraria (Dízimos/Ofertas).
- `audit`: Auditoria automática de alterações.
- `shared`: Elementos comuns (TenantEntity, DomainEvent).

### Camadas de Código
Dentro de cada módulo, respeite a seguinte divisão:
- **Domain**: Entidades, Agregados, Value Objects e Contratos (Interfaces). Sem dependências externas.
- **Application**: Services que orquestram o negócio e DTOs.
- **Infrastructure**: Implementações técnicas (Persistência, Configurações, Integrações).
- **Web**: Controllers REST.

## 🔒 Multi-tenancy e Isolamento
- O sistema é **Multi-tenant** nativo.
- Use a classe base `TenantEntity` para entidades que pertencem a uma igreja.
- O isolamento é feito via `@TenantId` do Hibernate 6, usando a coluna `igreja_id`.
- O `tenant_id` é extraído automaticamente do JWT pelo `JwtAuthenticationFilter`.

## 📡 Comunicação e Eventos
- **Eventos de Domínio**: Devem ser registrados e disparados para manter o desacoplamento.
- **Integração (Kafka)**: Use o tópico `culto-updates` para notificar mudanças entre contextos.
- **Real-time (WebSockets)**: Utilizado para atualizar dashboards dinamicamente via STOMP.

## 📝 Auditoria
- Não implemente logs de auditoria manualmente nos Services.
- O `AuditAspect` (AOP) captura alterações em métodos de escrita e registra em `log_correcao` automaticamente.

## 🛠️ Desenvolvimento e Deploy
- **Docker**: Sempre mantenha o `docker-compose.yml` atualizado.
- **Build**: Use `./gradlew build` (requer Java 21 configurado).
- **Execução Local**: O comando padrão é `docker-compose up --build`.
- **Banco de Dados**: O `hibernate.ddl-auto` está como `update` para ambiente de desenvolvimento.

## 📖 Documentação Adicional
- Consulte sempre o `DOCUMENTATION.md` na raiz do projeto para detalhes técnicos profundos sobre endpoints e fluxos.
- Swagger disponível em: `http://localhost:8080/swagger-ui.html`.

---
*Nota: Ao realizar alterações, mantenha os comentários pedagógicos em português para facilitar o entendimento da arquitetura.*
