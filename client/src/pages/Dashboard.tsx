import { useEffect, useState } from "react";
import { useLocation, Link } from "wouter";
import Navbar from "@/components/Navbar";
import { useAuth } from "@/contexts/AuthContext";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { TrendingUp, Award, BookOpen, Sparkles } from "lucide-react";

export default function Dashboard() {
  const [, navigate] = useLocation();
  const { user, isAuthenticated, loading } = useAuth();

  useEffect(() => {
    if (!loading && !isAuthenticated) {
      navigate("/login");
    }
  }, [isAuthenticated, loading, navigate]);

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-emerald-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Carregando...</p>
        </div>
      </div>
    );
  }

  if (!user) return null;

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="container py-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">
            Bem-vindo(a), {user.nome}!
          </h1>
          <p className="text-gray-600 mt-2">
            Aqui está um resumo do seu progresso e próximos passos
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Carreiras Exploradas
              </CardTitle>
              <TrendingUp className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">0</div>
              <p className="text-xs text-muted-foreground">
                Explore carreiras disponíveis
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Competências
              </CardTitle>
              <Award className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">0</div>
              <p className="text-xs text-muted-foreground">
                Competências adquiridas
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Cursos
              </CardTitle>
              <BookOpen className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">0</div>
              <p className="text-xs text-muted-foreground">
                Cursos recomendados
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Trilhas
              </CardTitle>
              <Sparkles className="h-4 w-4 text-muted-foreground" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">0</div>
              <p className="text-xs text-muted-foreground">
                Trilhas personalizadas
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="grid md:grid-cols-2 gap-6">
          <Card>
            <CardHeader>
              <CardTitle>Comece sua jornada</CardTitle>
              <CardDescription>
                Explore carreiras e descubra seu caminho profissional
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <Link href="/carreiras">
                <Button className="w-full" variant="default">
                  <TrendingUp className="w-4 h-4 mr-2" />
                  Explorar Carreiras
                </Button>
              </Link>
              <p className="text-sm text-gray-600">
                Navegue por diversas oportunidades de carreira no setor de energia
                sustentável e encontre a que melhor se adequa ao seu perfil.
              </p>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Suas Trilhas</CardTitle>
              <CardDescription>
                Acompanhe seu progresso e próximos passos
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <Link href="/trilhas">
                <Button className="w-full" variant="outline">
                  <Sparkles className="w-4 h-4 mr-2" />
                  Ver Minhas Trilhas
                </Button>
              </Link>
              <p className="text-sm text-gray-600">
                Acesse suas trilhas de aprendizado personalizadas com recomendações
                de cursos e explicações geradas por IA.
              </p>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
