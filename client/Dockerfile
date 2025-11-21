FROM node:22-alpine

WORKDIR /app

# Instalar pnpm
RUN npm install -g pnpm

# Copiar arquivos do projeto
COPY package.json ./

# Instalar dependências
RUN pnpm install

# Copiar código-fonte
COPY . .

# Build da aplicação
RUN pnpm build

# Expor porta
EXPOSE 3000

# Comando para iniciar a aplicação
CMD ["pnpm", "preview"]
