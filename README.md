# CareerMap API - Global Solution 2025/2

Esta é a implementação da API REST em Java com Spring Boot para o projeto **CareerMap**, desenvolvida para a Global Solution 2025/2.

A API foi construída utilizando **Spring Boot 2.7.18** (compatível com Java 11) e implementa as funcionalidades de Gestão de Usuários, Autenticação JWT, Mapeamento de Carreira e Competências, além de simular a integração com IA (OpenAI) e aplicar boas práticas como JPA, Bean Validation, Paginação, Cache e Filas Assíncronas.

## 🚀 Tecnologias Utilizadas

*   **Linguagem:** Java 11
*   **Framework:** Spring Boot 2.7.18
*   **Persistência:** Spring Data JPA
*   **Banco de Dados:** H2 (em memória, para desenvolvimento)
*   **Segurança:** Spring Security + JWT (JJWT 0.11.5)
*   **Integração IA:** WebClient (simulando chamada à API do OpenAI)
*   **Boas Práticas:** Lombok, Bean Validation, Paginação, Cache (Caffeine), Async.

## ⚙️ Como Executar o Projeto

### Pré-requisitos

*   Java 11 ou superior (o projeto está configurado para Java 11)
*   Apache Maven

### Passos

1.  **Navegue até o diretório do projeto:**
    ```bash
    cd careermap-api
    ```

2.  **Compile e empacote o projeto:**
    ```bash
    mvn clean package
    ```

3.  **Execute o arquivo JAR gerado:**
    ```bash
    java -jar target/careermap-0.0.1-SNAPSHOT.jar
    ```

A API estará disponível em `http://localhost:8080`.

## 🔑 Endpoints da API

Todos os endpoints estão prefixados com `/api/v1`.

### 1. Autenticação e Usuários

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | Registra um novo usuário. |
| `POST` | `/api/v1/auth/login` | Realiza o login e retorna um token JWT. |
| `GET` | `/api/v1/usuarios/perfil` | Retorna o perfil do usuário autenticado. **(Requer JWT)** |
| `PUT` | `/api/v1/usuarios/perfil` | Atualiza o perfil do usuário autenticado. **(Requer JWT)** |
| `POST` | `/api/v1/usuarios/recuperar-senha` | Simulação de recuperação de senha (endpoint público). |

### 2. Mapeamento de Carreira e Competências

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/api/v1/carreiras` | Lista todas as carreiras com paginação. **(Requer JWT)** |
| `POST` | `/api/v1/carreiras` | Cria uma nova carreira. **(Requer JWT)** |
| `GET` | `/api/v1/competencias` | Lista todas as competências com paginação. **(Requer JWT)** |
| `POST` | `/api/v1/competencias` | Cria uma nova competência. **(Requer JWT)** |
| `GET` | `/api/v1/cursos` | Lista todos os cursos com paginação. **(Requer JWT)** |
| `POST` | `/api/v1/cursos` | Cria um novo curso. **(Requer JWT)** |

### 3. Trilha de Carreira e IA

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/v1/trilhas` | Cria uma nova trilha de carreira para o usuário autenticado. **(Requer JWT)** |
| `GET` | `/api/v1/trilhas` | Lista as trilhas do usuário autenticado com paginação. **(Requer JWT)** |
| `GET` | `/api/v1/trilhas/{id}/explicacao-ia` | Retorna a explicação da trilha gerada pela IA (simulada). **(Requer JWT)** |
| `GET` | `/api/v1/ia/explicar-trilha` | Endpoint de demonstração da integração com IA (simulada). **(Requer JWT)** |

## 💡 Boas Práticas Implementadas

*   **Internacionalização (i18n):** Configuração básica para mensagens de erro.
*   **Cache:** Uso de `@Cacheable` nos serviços de Carreira e Competência.
*   **Filas Assíncronas:** Uso de `@Async` no `AIService` para simular o processamento em segundo plano da explicação da IA.
*   **WebClient:** Utilizado no `AIService` para simular a chamada a uma API externa (OpenAI).
*   **Bean Validation:** Validação de entrada de dados nos DTOs.
*   **Paginação:** Implementada em todos os endpoints de listagem.
