// src/services/api.ts
import axios from "axios";

// Cria uma instância do Axios apontando para sua API
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});

// Função de registro de usuário
export async function register(data: { nome: string; email: string; senha: string }) {
  const response = await api.post("/auth/register", data);
  return response.data;
}

// Função de login (opcional, caso queira usar)
export async function login(data: { email: string; senha: string }) {
  const response = await api.post("/auth/login", data);
  return response.data;
}

// Exporta a instância do Axios caso precise em outras partes
export default api;
