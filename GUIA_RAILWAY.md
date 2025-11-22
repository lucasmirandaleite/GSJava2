# Guia de Configuração do Railway - CareerMap

## Problema Identificado

O login e cadastro não estão funcionando porque:

1. **Backend não está iniciando** - O arquivo `railway.toml` estava com o nome do JAR incorreto
2. **Frontend não consegue se comunicar com o backend** - Falta configurar a URL da API
3. **Variáveis de ambiente não configuradas** - Faltam configurações do banco de dados e JWT

## Correções Necessárias

### 1. Correção do railway.toml (JÁ CORRIGIDO)

O arquivo `railway.toml` foi corrigido para usar o nome correto do JAR:

```toml
[build]
builder = "NIXPACKS"

[deploy]
startCommand = "java -jar target/careermap-0.0.1-SNAPSHOT.jar"

[[services]]
httpPort = 8080
internalPort = 8080
```

**Ação necessária**: Fazer commit e push desta alteração para o GitHub.

### 2. Configurar Variáveis de Ambiente no Railway

#### Serviço Backend (GSJava2)

Acesse o painel do Railway > Serviço GSJava2 > Variables e adicione:

| Variável | Valor | Descrição |
|----------|-------|-----------|
| `DATABASE_URL` | (fornecido pelo Railway) | URL de conexão do PostgreSQL |
| `PGUSER` | (fornecido pelo Railway) | Usuário do banco de dados |
| `PGPASSWORD` | (fornecido pelo Railway) | Senha do banco de dados |
| `JWT_SECRET` | `sua-chave-secreta-aqui-2024` | Chave secreta para geração de tokens JWT |
| `PORT` | `8080` | Porta do servidor (opcional, padrão é 8080) |

**Nota**: Se você já tem um serviço PostgreSQL no Railway, ele fornece automaticamente as variáveis `DATABASE_URL`, `PGUSER` e `PGPASSWORD`. Caso contrário, você precisa criar um serviço PostgreSQL primeiro.

#### Serviço Frontend (hospitable-perception)

Acesse o painel do Railway > Serviço hospitable-perception > Variables e adicione:

| Variável | Valor | Descrição |
|----------|-------|-----------|
| `VITE_API_BASE_URL` | `https://gsjava2-production.up.railway.app/api/v1` | URL da API do backend |

**IMPORTANTE**: Variáveis que começam com `VITE_` são injetadas em **tempo de build**, não em runtime. Após adicionar esta variável, você precisa fazer um **redeploy** do frontend.

### 3. Conectar o Backend ao PostgreSQL

Se você ainda não tem um serviço PostgreSQL:

1. No Railway, clique em **"+ New"** > **"Database"** > **"Add PostgreSQL"**
2. O Railway criará automaticamente as variáveis de ambiente necessárias
3. No serviço do backend, vá em **"Settings"** > **"Service Variables"**
4. Verifique se as variáveis `DATABASE_URL`, `PGUSER` e `PGPASSWORD` estão presentes
5. Se não estiverem, você pode referenciá-las do serviço PostgreSQL usando:
   - `${{Postgres.DATABASE_URL}}`
   - `${{Postgres.PGUSER}}`
   - `${{Postgres.PGPASSWORD}}`

### 4. Fazer Deploy das Alterações

#### Backend:

```bash
cd /caminho/do/projeto/GSJava2
git add railway.toml
git commit -m "fix: corrigir nome do JAR no railway.toml"
git push origin main
```

O Railway detectará automaticamente o push e fará o redeploy.

#### Frontend:

Após configurar a variável `VITE_API_BASE_URL`:

1. No painel do Railway, vá para o serviço frontend
2. Clique em **"Deployments"**
3. Clique nos três pontos do último deployment
4. Selecione **"Redeploy"**

Ou faça um commit vazio para forçar o rebuild:

```bash
cd /caminho/do/projeto/GSJava2/client
git commit --allow-empty -m "chore: rebuild frontend com variáveis de ambiente"
git push origin main
```

## Verificação

### 1. Verificar se o backend está rodando:

Acesse no navegador:
```
https://gsjava2-production.up.railway.app/api/v1/auth/health
```

Deve retornar: `Auth service is healthy`

### 2. Verificar se o frontend está conectando:

1. Acesse: `https://hospitable-perception-production-91e3.up.railway.app/register`
2. Preencha o formulário de cadastro
3. Clique em "Cadastro"
4. Abra o console do navegador (F12) e verifique se não há erros de conexão

### 3. Testar o cadastro:

Se tudo estiver configurado corretamente:
- O cadastro deve funcionar e redirecionar para a página de login
- O login deve funcionar e redirecionar para a página principal

## Solução de Problemas

### Backend ainda retorna 502:

1. Verifique os logs do deploy no Railway
2. Certifique-se de que o PostgreSQL está rodando
3. Verifique se todas as variáveis de ambiente estão configuradas
4. Verifique se o JAR foi construído corretamente (veja os logs de build)

### Frontend ainda não conecta:

1. Verifique se a variável `VITE_API_BASE_URL` está configurada
2. Certifique-se de que fez o **redeploy** após adicionar a variável
3. Abra o console do navegador e verifique a URL que está sendo chamada
4. Verifique se o backend está respondendo corretamente

### Erro de CORS:

Se você ver erros de CORS no console, verifique:
1. O `@CrossOrigin(origins = "*")` está presente no `AuthController.java`
2. Se você tem um `SecurityConfig.java`, certifique-se de que o CORS está habilitado

## Arquitetura Final

```
┌─────────────────────────────────────────────────────────────┐
│                        Railway                               │
│                                                              │
│  ┌──────────────────┐         ┌──────────────────┐         │
│  │   Frontend       │         │    Backend       │         │
│  │  (React/Vite)    │────────▶│  (Spring Boot)   │         │
│  │  Port: 3000      │  API    │  Port: 8080      │         │
│  │                  │ Calls   │                  │         │
│  └──────────────────┘         └────────┬─────────┘         │
│                                         │                    │
│                                         │                    │
│                                ┌────────▼─────────┐         │
│                                │   PostgreSQL     │         │
│                                │   Database       │         │
│                                └──────────────────┘         │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## Resumo das Ações

- [x] Corrigir `railway.toml` com o nome correto do JAR
- [ ] Fazer commit e push da correção
- [ ] Configurar variáveis de ambiente do backend no Railway
- [ ] Configurar variável `VITE_API_BASE_URL` do frontend no Railway
- [ ] Fazer redeploy do backend
- [ ] Fazer redeploy do frontend
- [ ] Testar cadastro e login

## Suporte

Se após seguir todos os passos o problema persistir:
1. Verifique os logs de deploy no Railway
2. Verifique os logs de runtime no Railway
3. Abra o console do navegador (F12) e veja os erros
4. Compartilhe os logs para análise mais detalhada
