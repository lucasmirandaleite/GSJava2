import { useState } from "react";
import { useLocation, useNavigate } from "wouter";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useAuth } from "@/contexts/AuthContext";
import { toast } from "sonner";
import { Link } from "wouter";
import { Zap } from "lucide-react";

export default function Login() {
  const [, navigate] = useLocation();
  const { login } = useAuth();
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    email: "",
    senha: "",
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      // Chama a função login do AuthContext
      await login(formData);
      
      // Se chegou aqui, login foi bem-sucedido
      toast.success("Login realizado com sucesso!");
      
      // Aguarda um pouco e depois redireciona
      setTimeout(() => {
        navigate("/dashboard");
      }, 500);
      
    } catch (error: any) {
      // Mostra erro específico
      const errorMsg = error.response?.data?.message || 
                       error.message || 
                       "Erro ao fazer login";
      toast.error(errorMsg);
      console.error("Erro login:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-emerald-50 via-white to-emerald-50">
      <div className="w-full max-w-md px-4">
        <div className="text-center mb-8">
          <Link href="/" className="inline-flex items-center gap-2 text-2xl font-bold text-emerald-600 hover:text-emerald-700">
            <Zap className="w-8 h-8" />
            CareerMap
          </Link>
        </div>

        <Card className="border-0 shadow-lg">
          <CardHeader className="space-y-2">
            <CardTitle>Entrar</CardTitle>
            <CardDescription>Entre com suas credenciais para acessar sua conta</CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="seu@email.com"
                  value={formData.email}
                  onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                  required
                  disabled={loading}
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="senha">Senha</Label>
                <Input
                  id="senha"
                  type="password"
                  placeholder="••••••••"
                  value={formData.senha}
                  onChange={(e) => setFormData({ ...formData, senha: e.target.value })}
                  required
                  disabled={loading}
                />
              </div>
              <Button 
                type="submit" 
                className="w-full bg-blue-600 hover:bg-blue-700"
                disabled={loading}
              >
                {loading ? "Entrando..." : "Entrar"}
              </Button>
            </form>
            <div className="mt-4 text-center text-sm">
              <span className="text-gray-600">Não tem uma conta? </span>
              <Link href="/register" className="text-emerald-600 hover:text-emerald-700 font-medium">
                Cadastre-se
              </Link>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
