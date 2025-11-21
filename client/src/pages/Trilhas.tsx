import { useEffect, useState } from "react";
import { useLocation } from "wouter";
import Navbar from "@/components/Navbar";
import { useAuth } from "@/contexts/AuthContext";
import { api, Trilha, PageResponse } from "@/lib/api";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Sparkles, BookOpen, Loader2, Lightbulb } from "lucide-react";
import { toast } from "sonner";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog";

export default function Trilhas() {
  const [, navigate] = useLocation();
  const { user, isAuthenticated, loading: authLoading } = useAuth();
  const [trilhas, setTrilhas] = useState<PageResponse<Trilha> | null>(null);
  const [loading, setLoading] = useState(true);
  const [selectedTrilha, setSelectedTrilha] = useState<Trilha | null>(null);
  const [explicacaoIA, setExplicacaoIA] = useState<string>("");
  const [loadingExplicacao, setLoadingExplicacao] = useState(false);

  useEffect(() => {
    if (!authLoading && !isAuthenticated) {
      navigate("/login");
    } else if (isAuthenticated) {
      loadTrilhas();
    }
  }, [isAuthenticated, authLoading, navigate]);

  const loadTrilhas = async () => {
    try {
      setLoading(true);
      const data = await api.getTrilhas(0, 10);
      setTrilhas(data);
    } catch (error: any) {
      toast.error(error.message || "Erro ao carregar trilhas");
    } finally {
      setLoading(false);
    }
  };

  const handleVerExplicacao = async (trilha: Trilha) => {
    setSelectedTrilha(trilha);
    setLoadingExplicacao(true);

    try {
      const explicacao = await api.getExplicacaoIA(trilha.id);
      setExplicacaoIA(explicacao);
    } catch (error: any) {
      toast.error(error.message || "Erro ao carregar explicação");
      setExplicacaoIA("Explicação não disponível no momento.");
    } finally {
      setLoadingExplicacao(false);
    }
  };

  if (authLoading || loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="container py-20 flex justify-center">
          <Loader2 className="w-8 h-8 animate-spin text-emerald-600" />
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="container py-12">
        <div className="mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">
            Minhas Trilhas de Aprendizado
          </h1>
          <p className="text-lg text-gray-600">
            Acompanhe suas trilhas personalizadas com recomendações de cursos e
            explicações geradas por IA
          </p>
        </div>

        {trilhas && trilhas.content.length > 0 ? (
          <div className="grid md:grid-cols-2 gap-6">
            {trilhas.content.map((trilha) => (
              <Card key={trilha.id} className="hover:shadow-lg transition-shadow">
                <CardHeader>
                  <div className="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center mb-3">
                    <Sparkles className="w-6 h-6 text-purple-600" />
                  </div>
                  <CardTitle>{trilha.carreiraAlvo.nome}</CardTitle>
                  <CardDescription>
                    Trilha personalizada para sua carreira em energia sustentável
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                  <div>
                    <h4 className="font-semibold text-sm text-gray-700 mb-2 flex items-center gap-2">
                      <BookOpen className="w-4 h-4" />
                      Cursos Recomendados ({trilha.cursosRecomendados.length})
                    </h4>
                    <div className="space-y-2">
                      {trilha.cursosRecomendados.slice(0, 3).map((curso) => (
                        <div
                          key={curso.id}
                          className="p-3 bg-gray-50 rounded-lg border border-gray-200"
                        >
                          <p className="font-medium text-sm text-gray-900">
                            {curso.nome}
                          </p>
                          <p className="text-xs text-gray-600 mt-1">
                            Duração: {curso.duracaoHoras}h
                          </p>
                        </div>
                      ))}
                      {trilha.cursosRecomendados.length > 3 && (
                        <p className="text-xs text-gray-600 text-center">
                          +{trilha.cursosRecomendados.length - 3} cursos adicionais
                        </p>
                      )}
                    </div>
                  </div>

                  <Button
                    className="w-full gap-2"
                    variant="outline"
                    onClick={() => handleVerExplicacao(trilha)}
                  >
                    <Lightbulb className="w-4 h-4" />
                    Ver Explicação com IA
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>
        ) : (
          <div className="text-center py-20">
            <Sparkles className="w-16 h-16 text-gray-400 mx-auto mb-4" />
            <h3 className="text-xl font-semibold text-gray-900 mb-2">
              Nenhuma trilha criada ainda
            </h3>
            <p className="text-gray-600 mb-6">
              Explore carreiras e crie sua primeira trilha de aprendizado
              personalizada
            </p>
            <Button onClick={() => navigate("/carreiras")}>
              Explorar Carreiras
            </Button>
          </div>
        )}
      </div>

      {/* Dialog de Explicação IA */}
      <Dialog
        open={!!selectedTrilha}
        onOpenChange={(open) => !open && setSelectedTrilha(null)}
      >
        <DialogContent className="max-w-2xl max-h-[80vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle className="flex items-center gap-2">
              <Lightbulb className="w-5 h-5 text-yellow-500" />
              Explicação Gerada por IA
            </DialogTitle>
            <DialogDescription>
              {selectedTrilha?.carreiraAlvo.nome}
            </DialogDescription>
          </DialogHeader>

          <div className="mt-4">
            {loadingExplicacao ? (
              <div className="flex justify-center py-8">
                <Loader2 className="w-6 h-6 animate-spin text-emerald-600" />
              </div>
            ) : (
              <div className="prose prose-sm max-w-none">
                <p className="text-gray-700 whitespace-pre-wrap">{explicacaoIA}</p>
              </div>
            )}
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
