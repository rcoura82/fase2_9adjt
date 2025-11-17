# Sistema de Gerenciamento de Restaurantes

Sistema completo de gerenciamento de restaurantes e cardápios desenvolvido com **Java 17**, **Spring Boot 3.3.5**, **JPA**, **H2 Database**, **Maven** e **Lombok**, seguindo os princípios de **Clean Architecture**.

## 🏗️ Arquitetura

O projeto segue Clean Architecture com separação em camadas:

### Camadas da Aplicação

```
src/main/java/com/restaurant/system/
├── domain/                    # Camada de Domínio
│   ├── model/                # Entidades de negócio
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
    │   ├── controller/      # Endpoints da API
    │   └── exception/       # Exception Handlers
    └── config/              # Configurações
```

### Princípios Aplicados

- **Clean Architecture**: Separação de responsabilidades em camadas
- **Dependency Inversion**: Dependências apontam para abstrações
- **Single Responsibility**: Cada classe tem uma única responsabilidade
- **Open/Closed**: Aberto para extensão, fechado para modificação

## 🚀 Tecnologias

- **Java 17** (recomenda-se Java 21 para produção)
- **Spring Boot 3.3.5**
- **Spring Data JPA**
- **H2 Database** (em memória)
- **Maven** (gerenciamento de dependências)
- **Lombok** (redução de boilerplate)
- **Springdoc OpenAPI** (documentação Swagger)
- **JUnit 5 & Mockito** (testes)

## 📋 Funcionalidades

### CRUD Completo

#### 1. **Restaurantes** (`/api/restaurants`)
- Criar restaurante
- Listar todos os restaurantes
- Buscar restaurante por ID
- Buscar restaurantes por dono
- Atualizar restaurante
- Deletar restaurante

**Campos:**
- Nome
- Endereço
- Tipo de Cozinha
- Horário de Funcionamento
- Dono (ID do usuário responsável)

#### 2. **Itens do Cardápio** (`/api/menu-items`)
- Criar item do cardápio
- Listar todos os itens
- Buscar item por ID
- Buscar itens por restaurante
- Atualizar item
- Deletar item

**Campos:**
- Nome
- Descrição
- Preço
- Disponível apenas no Restaurante (boolean)
- Caminho da Foto
- Restaurante (ID do restaurante)

#### 3. **Usuários** (`/api/users`)
- Criar usuário
- Listar todos os usuários
- Buscar usuário por ID
- Buscar usuário por username
- Atualizar usuário
- Deletar usuário

**Campos:**
- Username (único)
- Email
- Password

#### 4. **Tipos de Usuário** (`/api/user-types`)
- Criar tipo de usuário
- Listar todos os tipos
- Buscar tipo por ID
- Atualizar tipo
- Deletar tipo

**Campos:**
- Nome (único)
- Descrição

## 📡 Endpoints da API

### Restaurants

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/restaurants` | Criar novo restaurante |
| GET | `/api/restaurants` | Listar todos os restaurantes |
| GET | `/api/restaurants/{id}` | Buscar restaurante por ID |
| GET | `/api/restaurants/owner/{donoId}` | Listar restaurantes por dono |
| PUT | `/api/restaurants/{id}` | Atualizar restaurante |
| DELETE | `/api/restaurants/{id}` | Deletar restaurante |

### Menu Items

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/menu-items` | Criar novo item |
| GET | `/api/menu-items` | Listar todos os itens |
| GET | `/api/menu-items/{id}` | Buscar item por ID |
| GET | `/api/menu-items/restaurant/{restauranteId}` | Listar itens por restaurante |
| PUT | `/api/menu-items/{id}` | Atualizar item |
| DELETE | `/api/menu-items/{id}` | Deletar item |

### Users

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/users` | Criar novo usuário |
| GET | `/api/users` | Listar todos os usuários |
| GET | `/api/users/{id}` | Buscar usuário por ID |
| GET | `/api/users/username/{username}` | Buscar por username |
| PUT | `/api/users/{id}` | Atualizar usuário |
| DELETE | `/api/users/{id}` | Deletar usuário |

### User Types

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/user-types` | Criar novo tipo |
| GET | `/api/user-types` | Listar todos os tipos |
| GET | `/api/user-types/{id}` | Buscar tipo por ID |
| PUT | `/api/user-types/{id}` | Atualizar tipo |
| DELETE | `/api/user-types/{id}` | Deletar tipo |

## 🛠️ Configuração e Execução

### Pré-requisitos

- Java 17+ (recomendado Java 21)
- Maven 3.6+
- Docker (opcional, para containerização)

### Executar Localmente

1. **Clone o repositório:**
```bash
git clone https://github.com/rcoura82/fase2_9adjt.git
cd fase2_9adjt
```

2. **Compile o projeto:**
```bash
mvn clean install
```

3. **Execute a aplicação:**
```bash
mvn spring-boot:run
```

4. **Acesse:**
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:restaurantdb`
  - Username: `sa`
  - Password: (deixe em branco)

### Executar com Docker

1. **Build e execute:**
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

### Postman Collection

Importe o arquivo `postman_collection.json` no Postman para testar todos os endpoints.

## 🧪 Testes

### Executar todos os testes

```bash
mvn test
```

### Cobertura de Testes

- **Cobertura**: 80%+
- **Testes Unitários**: Casos de uso e lógica de negócio
- **Testes de Integração**: Controllers e fluxos completos

## 📁 Estrutura do Projeto

```
fase2_9adjt/
├── src/
│   ├── main/
│   │   ├── java/com/restaurant/system/
│   │   │   ├── domain/              # Entidades e interfaces de repositório
│   │   │   ├── application/         # DTOs, use cases, exceções
│   │   │   └── infrastructure/      # JPA, controllers, configurações
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/restaurant/system/
│           ├── application/usecase/  # Testes unitários
│           └── infrastructure/web/   # Testes de integração
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── postman_collection.json
└── README.md
```

## 📝 Exemplos de Requisições

### Criar Usuário

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "password": "senha123"
  }'
```

### Criar Restaurante

```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Restaurante Italiano",
    "endereco": "Rua das Flores, 123",
    "tipoCozinha": "Italiana",
    "horarioFuncionamento": "11h-23h",
    "donoId": 1
  }'
```

### Criar Item do Cardápio

```bash
curl -X POST http://localhost:8080/api/menu-items \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pizza Margherita",
    "descricao": "Pizza tradicional italiana",
    "preco": 35.90,
    "disponivelApenasRestaurante": false,
    "fotoCaminho": "/media/pizzas/margherita.jpg",
    "restauranteId": 1
  }'
```

## 📄 Licença

Este projeto é desenvolvido para fins educacionais - FASE 2 - 9ADJT
