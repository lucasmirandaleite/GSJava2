// contexts/AuthContext.tsx
import { createContext, useState, useEffect, useContext } from "react";
import { login as loginAPI, register as registerAPI } from "@/services/api";

interface AuthContextType {
  token: string | null;
  user: any | null;
  login: (credentials: any) => Promise<void>;
  register: (data: any) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(() => {
    return localStorage.getItem("token");
  });
  const [user, setUser] = useState<any | null>(() => {
    const stored = localStorage.getItem("user");
    return stored ? JSON.parse(stored) : null;
  });

  useEffect(() => {
    if (token) {
      localStorage.setItem("token", token);
    } else {
      localStorage.removeItem("token");
    }
  }, [token]);

  useEffect(() => {
    if (user) {
      localStorage.setItem("user", JSON.stringify(user));
    } else {
      localStorage.removeItem("user");
    }
  }, [user]);

  const login = async (credentials: any) => {
    try {
      const response = await loginAPI(credentials);
      
      // Backend retorna { mensagem: "...", usuario: {...} }
      if (response.usuario) {
        // Gera um token simples (ou pode vir do backend futuramente)
        const token = response.usuario.id?.toString() || "user-token";
        setToken(token);
        setUser(response.usuario);
        return response;
      }
    } catch (error) {
      throw error;
    }
  };

  const register = async (data: any) => {
    try {
      const response = await registerAPI(data);
      
      // Backend retorna { mensagem: "...", usuario: {...} }
      if (response.usuario) {
        const token = response.usuario.id?.toString() || "user-token";
        setToken(token);
        setUser(response.usuario);
        return response;
      }
    } catch (error) {
      throw error;
    }
  };

  const logout = () => {
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ 
      token, 
      user, 
      login, 
      register,
      logout, 
      isAuthenticated: !!token 
    }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext); 

  if (!context) {
    throw new Error("useAuth deve ser usado dentro de AuthProvider");
  }

  return context;
}
