# Sistema de Gerenciamento Hospitalar - Hospital Management System

Sistema completo de gerenciamento hospitalar com agendamento de consultas, desenvolvido com **Java 17**, **Spring Boot 3.3.5**, **Spring Security**, **GraphQL**, **RabbitMQ**, **JPA**, **H2 Database**, **Maven** e **Lombok**, seguindo os princípios de **Clean Architecture**.

## 🏗️ Arquitetura

O projeto segue Clean Architecture com separação em camadas e múltiplos serviços:

### Camadas da Aplicação

```
src/main/java/com/restaurant/system/
├── domain/                    # Camada de Domínio
│   ├── model/                # Entidades de negócio (User, Appointment)
│   └── repository/           # Interfaces de repositório (Ports)
├── application/              # Camada de Aplicação
│   ├── dto/                  # Data Transfer Objects
│   ├── usecase/              # Casos de uso (Services)
│   └── exception/            # Exceções de negócio
└── infrastructure/           # Camada de Infraestrutura
    ├── persistence/          # Implementação JPA
    │   ├── entity/          # Entidades JPA
    │   └── repository/      # Repositórios JPA (Adapters)
    ├── web/                 # Controllers REST
    │   ├── controller/      # Endpoints da API REST
    │   └── exception/       # Exception Handlers
    ├── graphql/             # Controllers GraphQL
    ├── messaging/           # RabbitMQ Producers e Consumers
    ├── security/            # Spring Security (UserDetailsService)
    └── config/              # Configurações (Security, RabbitMQ)
```

### Princípios Aplicados

- **Clean Architecture**: Separação de responsabilidades em camadas
- **Dependency Inversion**: Dependências apontam para abstrações
- **Single Responsibility**: Cada classe tem uma única responsabilidade
- **Open/Closed**: Aberto para extensão, fechado para modificação
- **Segurança**: Autenticação e autorização com Spring Security
- **Comunicação Assíncrona**: RabbitMQ para notificações
- **Flexibilidade de Consultas**: GraphQL para queries complexas

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Security** (Autenticação e Autorização)
- **Spring Data JPA**
- **GraphQL** (Consultas flexíveis)
- **RabbitMQ** (Mensageria assíncrona)
- **H2 Database** (em memória)
- **Maven** (gerenciamento de dependências)
- **Lombok** (redução de boilerplate)
- **Springdoc OpenAPI** (documentação Swagger)
- **JUnit 5 & Mockito** (testes)

## 📋 Funcionalidades

### 1. Segurança e Autenticação

#### Roles (Papéis)
- **PATIENT** (Paciente): Pode visualizar apenas suas consultas
- **NURSE** (Enfermeiro): Pode registrar consultas e acessar histórico
- **DOCTOR** (Médico): Pode visualizar e editar histórico de consultas

#### Autenticação
- **Basic Auth** (HTTP Basic Authentication)
- **BCrypt** para codificação de senhas
- Controle de acesso baseado em roles (`@PreAuthorize`)

### 2. Gestão de Usuários (`/api/users`)

- Criar usuário (registro público)
- Listar todos os usuários (DOCTOR, NURSE)
- Buscar usuário por ID
- Buscar usuário por username
- Atualizar usuário
- Deletar usuário

**Campos:**
- Username (único)
- Email
- Password (criptografado com BCrypt)
- Role (PATIENT, NURSE, DOCTOR)
- Full Name
- Specialty (para médicos)

### 3. Gestão de Consultas (`/api/appointments`)

#### REST API
- **POST** `/api/appointments` - Criar nova consulta (DOCTOR, NURSE)
- **GET** `/api/appointments/{id}` - Buscar consulta por ID
- **GET** `/api/appointments` - Listar todas as consultas (DOCTOR, NURSE)
- **GET** `/api/appointments/patient/{patientId}` - Listar consultas de um paciente
- **GET** `/api/appointments/patient/{patientId}/future` - Listar consultas futuras de um paciente
- **GET** `/api/appointments/doctor/{doctorId}` - Listar consultas de um médico (DOCTOR, NURSE)
- **PUT** `/api/appointments/{id}` - Atualizar consulta (DOCTOR, NURSE)
- **DELETE** `/api/appointments/{id}` - Deletar consulta (DOCTOR, NURSE)

**Campos de Consulta:**
- Patient ID
- Patient Name
- Doctor ID
- Doctor Name
- Appointment Date
- Specialty
- Notes
- Status (SCHEDULED, CONFIRMED, COMPLETED, CANCELLED)

### 4. GraphQL API (`/graphql`)

#### Queries
```graphql
# Buscar consulta por ID
appointment(id: ID!): Appointment

# Listar todas as consultas
appointments: [Appointment!]!

# Listar consultas de um paciente
appointmentsByPatient(patientId: ID!): [Appointment!]!

# Listar consultas futuras de um paciente
futureAppointmentsByPatient(patientId: ID!): [Appointment!]!

# Listar consultas de um médico
appointmentsByDoctor(doctorId: ID!): [Appointment!]!

# Buscar usuário por ID
user(id: ID!): User

# Listar todos os usuários
users: [User!]!
```

#### Mutations
```graphql
# Criar nova consulta
createAppointment(input: AppointmentInput!): Appointment!

# Atualizar consulta
updateAppointment(id: ID!, input: AppointmentUpdateInput!): Appointment!

# Deletar consulta
deleteAppointment(id: ID!): Boolean!
```

### 5. Sistema de Notificações (RabbitMQ)

- **Producer**: Publica eventos quando consultas são criadas ou atualizadas
- **Consumer**: Processa eventos e envia notificações aos pacientes
- **Exchange**: `appointment.exchange` (TopicExchange)
- **Queue**: `appointment.queue`
- **Routing Key**: `appointment.notification`

**Eventos:**
- `CREATED`: Consulta criada
- `UPDATED`: Consulta atualizada
- `CANCELLED`: Consulta cancelada

## 📡 Endpoints da API

### Authentication
Todas as requisições (exceto registro de usuário) requerem autenticação Basic Auth:
```
Authorization: Basic base64(username:password)
```

### Users

| Método | Endpoint | Descrição | Acesso |
|--------|----------|-----------|--------|
| POST | `/api/users` | Criar novo usuário (registro) | Público |
| GET | `/api/users` | Listar todos os usuários | DOCTOR, NURSE |
| GET | `/api/users/{id}` | Buscar usuário por ID | Autenticado |
| GET | `/api/users/username/{username}` | Buscar por username | Autenticado |
| PUT | `/api/users/{id}` | Atualizar usuário | Autenticado |
| DELETE | `/api/users/{id}` | Deletar usuário | Autenticado |

### Appointments

| Método | Endpoint | Descrição | Acesso |
|--------|----------|-----------|--------|
| POST | `/api/appointments` | Criar nova consulta | DOCTOR, NURSE |
| GET | `/api/appointments` | Listar todas as consultas | DOCTOR, NURSE |
| GET | `/api/appointments/{id}` | Buscar consulta por ID | Todos |
| GET | `/api/appointments/patient/{patientId}` | Listar consultas por paciente | Todos |
| GET | `/api/appointments/patient/{patientId}/future` | Listar consultas futuras | Todos |
| GET | `/api/appointments/doctor/{doctorId}` | Listar consultas por médico | DOCTOR, NURSE |
| PUT | `/api/appointments/{id}` | Atualizar consulta | DOCTOR, NURSE |
| DELETE | `/api/appointments/{id}` | Deletar consulta | DOCTOR, NURSE |

## 🛠️ Configuração e Execução

### Pré-requisitos

- Java 17+ (recomendado Java 21)
- Maven 3.6+
- Docker e Docker Compose (para RabbitMQ)

### Executar Localmente

1. **Clone o repositório:**
```bash
git clone https://github.com/rcoura82/fase2_9adjt.git
cd fase2_9adjt
```

2. **Inicie o RabbitMQ com Docker:**
```bash
docker-compose up -d rabbitmq
```

3. **Compile o projeto:**
```bash
mvn clean install
```

4. **Execute a aplicação:**
```bash
mvn spring-boot:run
```

5. **Acesse:**
- API REST: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- GraphQL: http://localhost:8080/graphql
- GraphiQL: http://localhost:8080/graphiql
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:hospitaldb`
  - Username: `sa`
  - Password: (deixe em branco)
- RabbitMQ Management: http://localhost:15672
  - Username: `guest`
  - Password: `guest`

### Executar com Docker

1. **Build e execute todos os serviços:**
```bash
docker-compose up --build
```

2. **Parar:**
```bash
docker-compose down
```

## 📚 Documentação da API

### Swagger/OpenAPI

Acesse a documentação interativa em:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### GraphiQL

Interface interativa para testar queries GraphQL:
- **GraphiQL**: http://localhost:8080/graphiql

### Postman Collection

Importe o arquivo `postman_collection.json` no Postman para testar todos os endpoints REST.

## 🧪 Testes

### Executar todos os testes

```bash
mvn test
```

### Cobertura de Testes

- **Testes Unitários**: Casos de uso e lógica de negócio
- **Testes de Integração**: Controllers e fluxos completos

## 📝 Exemplos de Uso

### 1. Criar Usuário (Paciente)

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "joao.silva",
    "email": "joao@example.com",
    "password": "senha123",
    "role": "PATIENT",
    "fullName": "João Silva"
  }'
```

### 2. Criar Usuário (Médico)

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dra.maria",
    "email": "maria@example.com",
    "password": "senha123",
    "role": "DOCTOR",
    "fullName": "Dra. Maria Santos",
    "specialty": "Cardiologia"
  }'
```

### 3. Criar Consulta (autenticado como médico/enfermeiro)

```bash
curl -X POST http://localhost:8080/api/appointments \
  -u "dra.maria:senha123" \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorId": 2,
    "appointmentDate": "2026-01-15T10:00:00",
    "specialty": "Cardiologia",
    "notes": "Consulta de rotina"
  }'
```

### 4. Listar Consultas Futuras de um Paciente

```bash
curl -X GET http://localhost:8080/api/appointments/patient/1/future \
  -u "joao.silva:senha123"
```

### 5. Query GraphQL (Consultas de um Paciente)

```graphql
query {
  appointmentsByPatient(patientId: "1") {
    id
    patientName
    doctorName
    appointmentDate
    specialty
    status
    notes
  }
}
```

### 6. Mutation GraphQL (Criar Consulta)

```graphql
mutation {
  createAppointment(input: {
    patientId: "1"
    doctorId: "2"
    appointmentDate: "2026-01-15T10:00:00"
    specialty: "Cardiologia"
    notes: "Consulta de rotina"
  }) {
    id
    patientName
    doctorName
    appointmentDate
    status
  }
}
```

## 🔒 Segurança

### Práticas Implementadas

1. **Autenticação**: Spring Security com HTTP Basic Auth
2. **Criptografia**: BCrypt para senhas
3. **Autorização**: Role-based access control (RBAC)
4. **Validação**: Bean Validation nas DTOs
5. **CORS**: Configurável para ambientes de produção
6. **CSRF**: Desabilitado para API stateless (pode ser habilitado conforme necessidade)

### Níveis de Acesso

- **PATIENT**: Visualiza apenas suas próprias consultas
- **NURSE**: Cria e visualiza consultas, acessa históricos
- **DOCTOR**: Cria, visualiza e edita consultas, acessa históricos

## 🏥 Arquitetura de Serviços

### Serviço de Agendamento
- Gerencia CRUD de consultas
- Publica eventos no RabbitMQ quando consultas são criadas/atualizadas
- Controle de acesso baseado em roles

### Serviço de Notificações
- Consome eventos do RabbitMQ
- Envia notificações aos pacientes (simulado via logs)
- Processa eventos de forma assíncrona

### Serviço de Histórico (GraphQL)
- Permite consultas flexíveis sobre histórico médico
- Suporta filtros por paciente, médico, data
- Controle de acesso granular

## 📁 Estrutura do Projeto

```
fase2_9adjt/
├── src/
│   ├── main/
│   │   ├── java/com/restaurant/system/
│   │   │   ├── domain/              # Entidades e interfaces de repositório
│   │   │   ├── application/         # DTOs, use cases, exceções
│   │   │   └── infrastructure/      # JPA, controllers, GraphQL, RabbitMQ, Security
│   │   └── resources/
│   │       ├── graphql/
│   │       │   └── schema.graphqls  # Schema GraphQL
│   │       └── application.properties
│   └── test/
│       └── java/com/restaurant/system/
│           ├── application/usecase/  # Testes unitários
│           └── infrastructure/web/   # Testes de integração
├── docker-compose.yml               # Docker Compose (App + RabbitMQ)
├── Dockerfile
├── pom.xml
├── postman_collection.json         # Postman Collection
└── README.md
```

## 📊 Monitoramento

### RabbitMQ Management

Acesse http://localhost:15672 para:
- Visualizar filas e exchanges
- Monitorar mensagens
- Ver estatísticas de consumo

### H2 Console

Acesse http://localhost:8080/h2-console para:
- Visualizar dados em tempo real
- Executar queries SQL
- Verificar schema do banco

## 🎯 Requisitos Atendidos (Fase 3)

### ✅ Segurança em Aplicações Java
- Autenticação com Spring Security (HTTP Basic Auth)
- Níveis de acesso: DOCTOR, NURSE, PATIENT
- Criptografia de senhas com BCrypt
- Autorização baseada em roles

### ✅ Consultas e Histórico com GraphQL
- Implementação de GraphQL para consultas flexíveis
- Queries para histórico médico
- Filtros por paciente, médico, data
- Mutations para criar/atualizar consultas

### ✅ Separação em Múltiplos Serviços
- Serviço de Agendamento (CRUD de consultas)
- Serviço de Notificações (processamento assíncrono)
- Serviço de Histórico via GraphQL

### ✅ Comunicação Assíncrona com RabbitMQ
- Configuração de Exchange, Queue e Routing Key
- Publisher para eventos de consulta
- Consumer para processamento de notificações
- Eventos: CREATED, UPDATED, CANCELLED

### ✅ Qualidade do Código
- Clean Architecture
- Testes unitários e de integração
- Documentação com Swagger/OpenAPI
- Código organizado e modular

### ✅ Documentação do Projeto
- README detalhado
- Arquitetura documentada
- Instruções de setup e execução
- Exemplos de uso

### ✅ Collections para Teste
- Postman Collection disponível
- Exemplos de curl
- GraphQL queries de exemplo

## 📄 Licença

Este projeto é desenvolvido para fins educacionais - FASE 3 - 9ADJT

## 👥 Contribuidores

Desenvolvido pela turma 9ADJT - FIAP
