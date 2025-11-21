import { createContext, useContext, useState, useEffect, ReactNode } from "react";
import { api, Usuario, LoginCredentials, RegisterData } from "@/lib/api";

interface AuthContextType {
  user: Usuario | null;
  loading: boolean;
  login: (credentials: LoginCredentials) => Promise<void>;
  register: (data: RegisterData) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<Usuario | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Verificar se há token e carregar perfil do usuário
    const loadUser = async () => {
      if (api.isAuthenticated()) {
        try {
          const profile = await api.getProfile();
          setUser(profile);
        } catch (error) {
          console.error("Erro ao carregar perfil:", error);
          api.logout();
        }
      }
      setLoading(false);
    };

    loadUser();
  }, []);

  const login = async (credentials: LoginCredentials) => {
    await api.login(credentials);
    const profile = await api.getProfile();
    setUser(profile);
  };

  const register = async (data: RegisterData) => {
    await api.register(data);
    const profile = await api.getProfile();
    setUser(profile);
  };

  const logout = () => {
    api.logout();
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        login,
        register,
        logout,
        isAuthenticated: !!user,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth deve ser usado dentro de um AuthProvider");
  }
  return context;
}
