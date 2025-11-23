CareerMap API - Global Solution 2025

👥 Integrantes da Equipe

Lucas Miranda Leite RM:555161

Gusthavo Daniel De Souza RM:554681

Guilherme Damasio Roselli RM:555873

Esta é a implementação da API REST em Java com Spring Boot para o projeto CareerMap, desenvolvida para a Global Solution 2025/2.

A API foi construída utilizando Spring Boot 2.7.18 (compatível com Java 11) e implementa as funcionalidades de Gestão de Usuários, Autenticação, Mapeamento de Carreira e Competências, além de simular a integração com IA (OpenAI) e aplicar boas práticas como JPA, Bean Validation, Paginação, Cache e Filas Assíncronas.

📋 Índice

1.
🚀 Tecnologias Utilizadas

2.
⚙️ Como Executar o Projeto

3.
🔑 Endpoints da API

4.
💡 Boas Práticas Implementadas

🚀 Tecnologias Utilizadas

•
Linguagem: Java 11

•
Framework: Spring Boot 2.7.18

•
Persistência: Spring Data JPA

•
Banco de Dados: H2 (em memória, para desenvolvimento)

•
Segurança: Spring Security (Autenticação baseada em Sessão/Cookie)

•
Integração IA: WebClient (simulando chamada à API do OpenAI)

•
Boas Práticas: Lombok, Bean Validation, Paginação, Cache (Caffeine), Async.

⚙️ Como Executar o Projeto

Pré-requisitos

•
Java 11 ou superior (o projeto está configurado para Java 11)

•
Apache Maven

Passos

1.
Navegue até o diretório do projeto:

2.
Compile e empacote o projeto:

3.
Execute o arquivo JAR gerado:

A API estará disponível em http://localhost:8080.

🌐 Ambiente de Produção

A API está implantada e acessível publicamente no seguinte endereço:

•
URL Base: https://noble-grace-production-8e27.up.railway.app

Documentação (Swagger UI )

Após a execução local, a documentação interativa da API (Swagger UI) estará acessível em: http://localhost:8080/swagger-ui.html

🔑 Endpoints da API

Todos os endpoints estão prefixados com /api/v1.

1. Autenticação e Usuários

Método
Endpoint
Descrição
POST
/api/v1/auth/register
Registra um novo usuário.
POST
/api/v1/auth/login
Realiza o login.
GET
/api/v1/usuarios/perfil
Retorna o perfil do usuário autenticado.
PUT
/api/v1/usuarios/perfil
Atualiza o perfil do usuário autenticado.
POST
/api/v1/usuarios/recuperar-senha
Simulação de recuperação de senha (endpoint público ).


2. Mapeamento de Carreira e Competências

Método
Endpoint
Descrição
GET
/api/v1/carreiras
Lista todas as carreiras com paginação.
POST
/api/v1/carreiras
Cria uma nova carreira.
GET
/api/v1/competencias
Lista todas as competências com paginação.
POST
/api/v1/competencias
Cria uma nova competência.
GET
/api/v1/cursos
Lista todos os cursos com paginação.
POST
/api/v1/cursos
Cria um novo curso.


3. Trilha de Carreira e IA

Método
Endpoint
Descrição
POST
/api/v1/trilhas
Cria uma nova trilha de carreira para o usuário autenticado.
GET
/api/v1/trilhas
Lista as trilhas do usuário autenticado com paginação.
GET
/api/v1/trilhas/{id}/explicacao-ia
Retorna a explicação da trilha gerada pela IA (simulada).
GET
/api/v1/ia/explicar-trilha
Endpoint de demonstração da integração com IA (simulada).


💡 Boas Práticas Implementadas

•
Internacionalização (i18n): Configuração básica para mensagens de erro.

•
Cache: Uso de @Cacheable nos serviços de Carreira e Competência.

•
Filas Assíncronas: Uso de @Async no AIService para simular o processamento em segundo plano da explicação da IA.

•
WebClient: Utilizado no AIService para simular a chamada a uma API externa (OpenAI).

•
Bean Validation: Validação de entrada de dados nos DTOs.

•
Paginação: Implementada em todos os endpoints de listagem.

•
Documentação: Uso de SpringDoc/Swagger para documentação da API.

