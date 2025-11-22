# Instruções para Corrigir o Login e Cadastro no Railway

## 🔍 Problema Identificado

O login e cadastro não estão funcionando porque o **backend não está iniciando corretamente** no Railway. O erro 502 Bad Gateway indica que o serviço não está respondendo.

## 🎯 Causa Raiz

O arquivo `railway.toml` estava configurado com o nome errado do arquivo JAR:
- ❌ **Incorreto**: `gsjava2-0.0.1-SNAPSHOT.jar`
- ✅ **Correto**: `careermap-0.0.1-SNAPSHOT.jar`

Além disso, faltam configurações de variáveis de ambiente no Railway.

## ✅ Correções Aplicadas

Já corrigi o arquivo `railway.toml` para você. Agora você precisa:

### 1️⃣ Fazer Commit e Push da Correção

```bash
cd /caminho/do/seu/projeto/GSJava2
git add railway.toml
git commit -m "fix: corrigir nome do JAR no railway.toml"
git push origin main
```

### 2️⃣ Configurar Variáveis de Ambiente no Railway

#### Para o Backend (GSJava2):

1. Acesse o [Railway Dashboard](https://railway.app/dashboard)
2. Selecione seu projeto
3. Clique no serviço **GSJava2** (backend)
4. Vá em **Variables**
5. Adicione as seguintes variáveis:

```
DATABASE_URL = (copie do serviço PostgreSQL)
PGUSER = (copie do serviço PostgreSQL)
PGPASSWORD = (copie do serviço PostgreSQL)
JWT_SECRET = minha-chave-secreta-super-segura-2024
PORT = 8080
```

**Como obter as variáveis do PostgreSQL:**
- Se você já tem um serviço PostgreSQL no projeto, clique nele
- Vá em **Variables** e copie os valores de `DATABASE_URL`, `PGUSER` e `PGPASSWORD`
- Cole esses valores no serviço do backend

**Se você NÃO tem PostgreSQL:**
1. No projeto, clique em **+ New**
2. Selecione **Database** > **Add PostgreSQL**
3. Aguarde a criação do banco
4. Copie as variáveis e adicione no backend

#### Para o Frontend (hospitable-perception):

1. No Railway Dashboard, clique no serviço **hospitable-perception** (frontend)
2. Vá em **Variables**
3. Adicione a variável:

```
VITE_API_BASE_URL = https://gsjava2-production.up.railway.app/api/v1
```

**⚠️ IMPORTANTE**: Após adicionar esta variável, você DEVE fazer um **redeploy** do frontend, pois variáveis `VITE_*` são injetadas em tempo de build.

### 3️⃣ Fazer Redeploy dos Serviços

#### Backend:
Após fazer o push do código corrigido, o Railway fará o deploy automaticamente.

#### Frontend:
1. No Railway, vá para o serviço frontend
2. Clique em **Deployments**
3. Clique nos três pontos (...) do último deployment
4. Selecione **Redeploy**

### 4️⃣ Verificar se Funcionou

Após os deploys terminarem:

1. **Teste o backend**:
   - Acesse: https://gsjava2-production.up.railway.app/api/v1/auth/health
   - Deve retornar: `Auth service is healthy`

2. **Teste o cadastro**:
   - Acesse: https://hospitable-perception-production-91e3.up.railway.app/register
   - Preencha o formulário
   - Clique em "Cadastro"
   - Deve mostrar mensagem de sucesso e redirecionar para login

3. **Teste o login**:
   - Acesse: https://hospitable-perception-production-91e3.up.railway.app/login
   - Use o email e senha que você cadastrou
   - Deve fazer login com sucesso

## 📋 Checklist

- [ ] Fazer commit e push do `railway.toml` corrigido
- [ ] Configurar variáveis de ambiente do backend no Railway
- [ ] Configurar variável `VITE_API_BASE_URL` do frontend no Railway
- [ ] Aguardar redeploy do backend
- [ ] Fazer redeploy manual do frontend
- [ ] Testar endpoint de health do backend
- [ ] Testar cadastro de novo usuário
- [ ] Testar login com usuário cadastrado

## 🆘 Solução de Problemas

### Backend ainda retorna 502:

1. Vá no Railway > Serviço Backend > **Deployments**
2. Clique no último deployment e veja os **Logs**
3. Procure por erros como:
   - `Error creating bean` → Problema com banco de dados
   - `Port already in use` → Problema com porta
   - `ClassNotFoundException` → Problema com dependências

### Frontend ainda não conecta:

1. Abra o site do frontend
2. Pressione **F12** para abrir o Console do navegador
3. Tente fazer cadastro
4. Veja se aparece erro de conexão
5. Verifique se a URL da API está correta (deve ser `https://gsjava2-production.up.railway.app/api/v1`)

### Erro de CORS:

Se você ver erro de CORS no console:
1. Verifique se o `@CrossOrigin(origins = "*")` está no `AuthController.java`
2. Verifique se o backend está respondendo corretamente

## 📚 Arquivos de Referência

Criei os seguintes arquivos para te ajudar:

1. **GUIA_RAILWAY.md** - Guia completo e detalhado
2. **diagnostico.md** - Análise técnica do problema
3. **verificar-config.sh** - Script para verificar configurações
4. **client/.env.example** - Exemplo de variáveis de ambiente do frontend

## 🎓 Entendendo o Problema

O Railway precisa saber:
1. **Qual arquivo executar** (railway.toml) → Estava errado
2. **Onde conectar o banco** (variáveis de ambiente) → Faltando
3. **Onde está a API** (variável do frontend) → Faltando

Depois de corrigir esses três pontos, tudo funcionará! 🚀

## 💡 Dica Extra

Para evitar problemas no futuro, sempre que fizer deploy:
1. Verifique os logs de build
2. Verifique os logs de runtime
3. Teste os endpoints da API diretamente
4. Só depois teste pelo frontend

Boa sorte! 🍀
