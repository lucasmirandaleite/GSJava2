mport axios from "axios";

// Cria uma instância do Axios apontando para sua API
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api/v1";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Função de registro de usuário
export async function register(data: { nome: string; email: string; senha: string }) {
  try {
    const response = await api.post("/auth/register", data);
    return response.data;
  } catch (error: any) {
    throw error.response?.data || error.message;
  }
}

// Função de login
export async function login(data: { email: string; senha: string }) {
  try {
    const response = await api.post("/auth/login", data);
    return response.data;
  } catch (error: any) {
    throw error.response?.data || error.message;
  }
}

// Função para verificar health do backend
export async function checkHealth() {
  try {
    const response = await api.get("/auth/health");
    return response.data;
  } catch (error: any) {
    throw error.response?.data || error.message;
  }
}

// Exporta a instância do Axios caso precise em outras partes
export default api;
