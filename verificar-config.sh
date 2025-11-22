#!/bin/bash

echo "======================================"
echo "Verificação de Configuração - CareerMap"
echo "======================================"
echo ""

# Verificar railway.toml
echo "1. Verificando railway.toml..."
if grep -q "careermap-0.0.1-SNAPSHOT.jar" railway.toml; then
    echo "✓ Nome do JAR correto no railway.toml"
else
    echo "✗ ERRO: Nome do JAR incorreto no railway.toml"
    echo "  Deve ser: careermap-0.0.1-SNAPSHOT.jar"
fi
echo ""

# Verificar pom.xml
echo "2. Verificando pom.xml..."
if grep -q "<artifactId>careermap</artifactId>" pom.xml; then
    echo "✓ ArtifactId correto no pom.xml"
else
    echo "✗ AVISO: ArtifactId diferente no pom.xml"
fi
echo ""

# Verificar application.properties
echo "3. Verificando application.properties..."
if grep -q "DATABASE_URL" src/main/resources/application.properties; then
    echo "✓ Configuração de banco de dados presente"
else
    echo "✗ ERRO: Configuração de banco de dados não encontrada"
fi

if grep -q "jwt.secret" src/main/resources/application.properties; then
    echo "✓ Configuração JWT presente"
else
    echo "✗ ERRO: Configuração JWT não encontrada"
fi
echo ""

# Verificar frontend
echo "4. Verificando configuração do frontend..."
if [ -f "client/src/services/api.ts" ]; then
    echo "✓ Arquivo de API do frontend encontrado"
    if grep -q "VITE_API_BASE_URL" client/src/services/api.ts; then
        echo "✓ Variável VITE_API_BASE_URL configurada no código"
    else
        echo "✗ AVISO: VITE_API_BASE_URL não encontrada no código"
    fi
else
    echo "✗ ERRO: Arquivo de API do frontend não encontrado"
fi
echo ""

# Verificar estrutura de diretórios
echo "5. Verificando estrutura do projeto..."
if [ -d "src/main/java/com/fiap/careermap" ]; then
    echo "✓ Estrutura de diretórios do backend OK"
else
    echo "✗ ERRO: Estrutura de diretórios do backend incorreta"
fi

if [ -d "client/src" ]; then
    echo "✓ Estrutura de diretórios do frontend OK"
else
    echo "✗ ERRO: Estrutura de diretórios do frontend incorreta"
fi
echo ""

echo "======================================"
echo "Verificação concluída!"
echo "======================================"
echo ""
echo "Próximos passos:"
echo "1. Corrigir os erros encontrados acima (se houver)"
echo "2. Fazer commit e push das alterações"
echo "3. Configurar variáveis de ambiente no Railway"
echo "4. Fazer redeploy dos serviços"
echo ""
