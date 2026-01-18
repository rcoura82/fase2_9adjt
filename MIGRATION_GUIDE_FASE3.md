# Guia de Migração - Sistema Hospitalar para fase3_9adjt

Este guia contém instruções detalhadas para migrar o código do sistema de gerenciamento hospitalar para o novo repositório `fase3_9adjt`.

## Pré-requisitos

- Git instalado
- Acesso ao repositório https://github.com/rcoura82/fase3_9adjt
- Java 17+
- Maven 3.6+

## Opção 1: Migração Direta (Recomendada)

### Passo 1: Clone o repositório atual e navegue até o commit com o hospital system

```bash
# Clone o repositório fase2_9adjt
git clone https://github.com/rcoura82/fase2_9adjt.git
cd fase2_9adjt

# Checkout para o branch com as mudanças do hospital
git checkout copilot/implement-scheduling-system-backend

# Checkout para o commit antes da reversão (último commit com hospital system)
git checkout 55f29ee
```

### Passo 2: Configure o novo repositório

```bash
# Adicione o novo repositório como remote
git remote add fase3 https://github.com/rcoura82/fase3_9adjt.git

# Fetch o repositório (se já tiver conteúdo)
git fetch fase3

# Crie um novo branch para o push
git checkout -b main
```

### Passo 3: Push para o novo repositório

```bash
# Push para o repositório fase3_9adjt
git push fase3 main

# Ou se preferir forçar (se o repositório já tiver histórico):
git push -f fase3 main
```

## Opção 2: Migração Manual (Passo a Passo)

### Passo 1: Inicialize o novo repositório

```bash
# Clone o repositório vazio fase3_9adjt
git clone https://github.com/rcoura82/fase3_9adjt.git
cd fase3_9adjt

# Inicialize com gitignore
cat > .gitignore << 'EOF'
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/
EOF
```

### Passo 2: Copie os arquivos do sistema hospitalar

No diretório fase2_9adjt (no commit 55f29ee), copie os seguintes arquivos:

#### 2.1 Arquivos de Configuração Principal

```bash
# Do repositório fase2_9adjt (commit 55f29ee)
cp pom.xml ../fase3_9adjt/
cp docker-compose.yml ../fase3_9adjt/
cp Dockerfile ../fase3_9adjt/
cp README_HOSPITAL.md ../fase3_9adjt/README.md
cp IMPLEMENTATION_SUMMARY.md ../fase3_9adjt/
```

#### 2.2 Estrutura de Código Fonte

```bash
cd fase3_9adjt

# Crie a estrutura de diretórios
mkdir -p src/main/java/com/restaurant/system
mkdir -p src/main/resources
mkdir -p src/test/java/com/restaurant/system

# Volte para fase2_9adjt para copiar os arquivos
cd ../fase2_9adjt

# Copie toda a estrutura src/
cp -r src/main/java/com/restaurant/system/* ../fase3_9adjt/src/main/java/com/restaurant/system/
cp -r src/main/resources/* ../fase3_9adjt/src/main/resources/
cp -r src/test/java/com/restaurant/system/* ../fase3_9adjt/src/test/java/com/restaurant/system/
```

#### 2.3 Arquivos Maven

```bash
# Copie o wrapper do Maven
cp -r .mvn ../fase3_9adjt/
cp mvnw ../fase3_9adjt/
cp mvnw.cmd ../fase3_9adjt/
```

### Passo 3: Ajuste o README.md

```bash
cd ../fase3_9adjt

# O arquivo README_HOSPITAL.md já foi copiado como README.md
# Verifique e ajuste se necessário
```

### Passo 4: Commit e Push

```bash
cd ../fase3_9adjt

# Adicione todos os arquivos
git add .

# Faça o primeiro commit
git commit -m "Initial commit: Hospital Management System - Phase 3

Implements complete hospital management system with:
- Spring Security authentication/authorization (PATIENT, NURSE, DOCTOR)
- GraphQL API for flexible patient history queries
- RabbitMQ async messaging for notifications
- Multiple microservices architecture
- Clean Architecture principles
- Comprehensive documentation and tests"

# Push para o repositório
git push origin main
```

## Passo 5: Verifique a Instalação

```bash
# Compile o projeto
mvn clean compile

# Execute os testes
mvn test

# Execute a aplicação
mvn spring-boot:run
```

## Estrutura Final do Repositório fase3_9adjt

```
fase3_9adjt/
├── .gitignore
├── .mvn/
├── docker-compose.yml
├── Dockerfile
├── IMPLEMENTATION_SUMMARY.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/restaurant/system/
    │   │       ├── RestaurantSystemApplication.java
    │   │       ├── application/
    │   │       │   ├── dto/
    │   │       │   │   ├── AppointmentDTO.java
    │   │       │   │   ├── MenuItemDTO.java
    │   │       │   │   ├── RestaurantDTO.java
    │   │       │   │   └── UserDTO.java
    │   │       │   ├── exception/
    │   │       │   └── usecase/
    │   │       │       ├── AppointmentUseCase.java
    │   │       │       ├── MenuItemUseCase.java
    │   │       │       ├── RestaurantUseCase.java
    │   │       │       └── UserUseCase.java
    │   │       ├── domain/
    │   │       │   ├── model/
    │   │       │   │   ├── Appointment.java
    │   │       │   │   ├── AppointmentStatus.java
    │   │       │   │   ├── MenuItem.java
    │   │       │   │   ├── Restaurant.java
    │   │       │   │   ├── User.java
    │   │       │   │   └── UserRole.java
    │   │       │   └── repository/
    │   │       │       ├── AppointmentRepository.java
    │   │       │       ├── MenuItemRepository.java
    │   │       │       ├── RestaurantRepository.java
    │   │       │       └── UserRepository.java
    │   │       └── infrastructure/
    │   │           ├── config/
    │   │           │   ├── RabbitMQConfig.java
    │   │           │   └── SecurityConfig.java
    │   │           ├── graphql/
    │   │           │   ├── AppointmentGraphQLController.java
    │   │           │   └── UserGraphQLController.java
    │   │           ├── messaging/
    │   │           │   ├── AppointmentNotificationConsumer.java
    │   │           │   ├── AppointmentNotificationEvent.java
    │   │           │   └── AppointmentNotificationPublisher.java
    │   │           ├── persistence/
    │   │           ├── security/
    │   │           │   └── CustomUserDetailsService.java
    │   │           └── web/
    │   │               └── controller/
    │   │                   ├── AppointmentController.java
    │   │                   ├── MenuItemController.java
    │   │                   ├── RestaurantController.java
    │   │                   └── UserController.java
    │   └── resources/
    │       ├── application.properties
    │       └── graphql/
    │           └── schema.graphqls
    └── test/
        └── java/
            └── com/restaurant/system/
                ├── application/usecase/
                └── infrastructure/web/controller/
```

## Arquivos Principais a Verificar

### pom.xml
- GroupId: `com.restaurant`
- ArtifactId: `hospital-system`
- Dependências: Spring Security, GraphQL, RabbitMQ

### application.properties
- Nome da aplicação: `hospital-system`
- Database: `hospitaldb`
- RabbitMQ configurado

### docker-compose.yml
- Serviço RabbitMQ
- Serviço da aplicação hospital-system

## Endpoints Disponíveis

Após executar a aplicação, os seguintes endpoints estarão disponíveis:

- **REST API**: http://localhost:8080/api/
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **GraphQL**: http://localhost:8080/graphql
- **GraphiQL**: http://localhost:8080/graphiql
- **H2 Console**: http://localhost:8080/h2-console
- **RabbitMQ Management**: http://localhost:15672 (guest/guest)

## Troubleshooting

### Erro de compilação
```bash
# Limpe o cache do Maven
mvn clean

# Re-compile
mvn compile
```

### RabbitMQ não conecta
```bash
# Verifique se o RabbitMQ está rodando
docker-compose ps

# Inicie o RabbitMQ
docker-compose up -d rabbitmq
```

### Testes falhando
```bash
# Execute testes individualmente
mvn test -Dtest=AppointmentUseCaseTest

# Verifique logs
mvn test -X
```

## Próximos Passos

1. ✅ Verifique se todos os arquivos foram copiados corretamente
2. ✅ Execute `mvn clean test` para garantir que tudo funciona
3. ✅ Execute `mvn spring-boot:run` para testar a aplicação
4. ✅ Teste os endpoints REST e GraphQL
5. ✅ Verifique a documentação no README.md
6. ✅ Configure CI/CD se necessário

## Suporte

Se encontrar problemas:
1. Verifique o IMPLEMENTATION_SUMMARY.md para detalhes da arquitetura
2. Consulte o README.md para exemplos de uso
3. Revise os logs da aplicação

---

**Autor**: Sistema gerado automaticamente
**Data**: 2026-01-09
**Versão**: 1.0.0
