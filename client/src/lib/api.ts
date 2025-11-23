import { API_BASE_URL } from "../const";

export interface LoginCredentials {
  email: string;
  senha: string;
}

export interface RegisterData {
  nome: string;
  email: string;
  senha: string;
}

export interface AuthResponse {
  token: string;
}

export interface Usuario {
  id: number;
  nome: string;
  email: string;
  role: string;
}

export interface Carreira {
  id: number;
  nome: string;
  descricao: string;
  competenciasRequeridas: Competencia[];
}

export interface Competencia {
  id: number;
  nome: string;
  descricao: string;
}

export interface Curso {
  id: number;
  nome: string;
  descricao: string;
  duracaoHoras: number;
  competenciasAdquiridas: Competencia[];
}

export interface Trilha {
  id: number;
  usuario: Usuario;
  carreiraAlvo: Carreira;
  cursosRecomendados: Curso[];
  explicacaoIA?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

class ApiService {
  private baseUrl: string;
  private token: string | null = null;

  constructor() {
    this.baseUrl = API_BASE_URL;
    this.token = localStorage.getItem("auth_token");
  }

  private getHeaders(): HeadersInit {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };
    
    if (this.token) {
      headers["Authorization"] = `Bearer ${this.token}`;
    }
    
    return headers;
  }

  private async handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
      const error = await response.json().catch(() => ({ message: "Erro desconhecido" }));
      throw new Error(error.message || `Erro ${response.status}`);
    }
    return response.json();
  }

  // Auth
  async login(credentials: LoginCredentials): Promise<AuthResponse> {
    const response = await fetch(`${this.baseUrl}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(credentials),
    });
    const data = await this.handleResponse<AuthResponse>(response);
    this.token = data.token;
    localStorage.setItem("auth_token", data.token);
    return data;
  }

  async register(data: RegisterData): Promise<AuthResponse> {
    const response = await fetch(`${this.baseUrl}/usuarios/registrar`, {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(data),
});

    const authData = await this.handleResponse<AuthResponse>(response);
    this.token = authData.token;
    localStorage.setItem("auth_token", authData.token);
    return authData;
  }

  logout() {
    this.token = null;
    localStorage.removeItem("auth_token");
  }

  isAuthenticated(): boolean {
    return !!this.token;
  }

  // Usuários
  async getProfile(): Promise<Usuario> {
    const response = await fetch(`${this.baseUrl}/usuarios/perfil`, {
      headers: this.getHeaders(),
    });
    return this.handleResponse<Usuario>(response);
  }

  // Carreiras
  async getCarreiras(page = 0, size = 10): Promise<PageResponse<Carreira>> {
    const response = await fetch(
      `${this.baseUrl}/carreiras?page=${page}&size=${size}`,
      { headers: this.getHeaders() }
    );
    return this.handleResponse<PageResponse<Carreira>>(response);
  }

  async getCarreira(id: number): Promise<Carreira> {
    const response = await fetch(`${this.baseUrl}/carreiras/${id}`, {
      headers: this.getHeaders(),
    });
    return this.handleResponse<Carreira>(response);
  }

  async calcularCompatibilidade(
    carreiraId: number,
    competenciasIds: number[]
  ): Promise<number> {
    const response = await fetch(
      `${this.baseUrl}/carreiras/${carreiraId}/compatibilidade`,
      {
        method: "POST",
        headers: this.getHeaders(),
        body: JSON.stringify(competenciasIds),
      }
    );
    return this.handleResponse<number>(response);
  }

  // Competências
  async getCompetencias(page = 0, size = 10): Promise<PageResponse<Competencia>> {
    const response = await fetch(
      `${this.baseUrl}/competencias?page=${page}&size=${size}`,
      { headers: this.getHeaders() }
    );
    return this.handleResponse<PageResponse<Competencia>>(response);
  }

  // Cursos
  async getCursos(page = 0, size = 10): Promise<PageResponse<Curso>> {
    const response = await fetch(
      `${this.baseUrl}/cursos?page=${page}&size=${size}`,
      { headers: this.getHeaders() }
    );
    return this.handleResponse<PageResponse<Curso>>(response);
  }

  // Trilhas
  async getTrilhas(page = 0, size = 10): Promise<PageResponse<Trilha>> {
    const response = await fetch(
      `${this.baseUrl}/trilhas?page=${page}&size=${size}`,
      { headers: this.getHeaders() }
    );
    return this.handleResponse<PageResponse<Trilha>>(response);
  }

  async criarTrilha(usuarioId: number, carreiraId: number): Promise<Trilha> {
    const response = await fetch(`${this.baseUrl}/trilhas`, {
      method: "POST",
      headers: this.getHeaders(),
      body: JSON.stringify({ usuarioId, carreiraAlvoId: carreiraId }),
    });
    return this.handleResponse<Trilha>(response);
  }

  async getExplicacaoIA(trilhaId: number): Promise<string> {
    const response = await fetch(`${this.baseUrl}/ia/explicar-trilha/${trilhaId}`, {
      headers: this.getHeaders(),
    });
    return this.handleResponse<string>(response);
  }
}

export const api = new ApiService();
