import { useEffect, useState } from "react";
import { useRoute, useLocation } from "wouter";
import Navbar from "@/components/Navbar";
import { api, Carreira } from "@/lib/api";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ArrowLeft, Briefcase, Loader2, Award } from "lucide-react";
import { toast } from "sonner";
import { useAuth } from "@/contexts/AuthContext";

export default function CarreiraDetalhes() {
  const [, params] = useRoute("/carreiras/:id");
  const [, navigate] = useLocation();
  const { isAuthenticated, user } = useAuth();
  const [carreira, setCarreira] = useState<Carreira | null>(null);
  const [loading, setLoading] = useState(true);
  const [criandoTrilha, setCriandoTrilha] = useState(false);

  useEffect(() => {
    if (params?.id) {
      loadCarreira(parseInt(params.id));
    }
  }, [params?.id]);

  const loadCarreira = async (id: number) => {
    try {
      setLoading(true);
      const data = await api.getCarreira(id);
      setCarreira(data);
    } catch (error: any) {
      toast.error(error.message || "Erro ao carregar carreira");
      navigate("/carreiras");
    } finally {
      setLoading(false);
    }
  };

  const handleCriarTrilha = async () => {
    if (!isAuthenticated || !user || !carreira) {
      toast.error("Você precisa estar logado para criar uma trilha");
      navigate("/login");
      return;
    }

    try {
      setCriandoTrilha(true);
      await api.criarTrilha(user.id, carreira.id);
      toast.success("Trilha criada com sucesso!");
      navigate("/trilhas");
    } catch (error: any) {
      toast.error(error.message || "Erro ao criar trilha");
    } finally {
      setCriandoTrilha(false);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="container py-20 flex justify-center">
          <Loader2 className="w-8 h-8 animate-spin text-emerald-600" />
        </div>
      </div>
    );
  }

  if (!carreira) return null;

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="container py-12">
        <Button
          variant="ghost"
          className="mb-6"
          onClick={() => navigate("/carreiras")}
        >
          <ArrowLeft className="w-4 h-4 mr-2" />
          Voltar para Carreiras
        </Button>

        <div className="grid lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2 space-y-6">
            <Card>
              <CardHeader>
                <div className="w-16 h-16 bg-emerald-100 rounded-xl flex items-center justify-center mb-4">
                  <Briefcase className="w-8 h-8 text-emerald-600" />
                </div>
                <CardTitle className="text-3xl">{carreira.nome}</CardTitle>
                <CardDescription className="text-base">
                  {carreira.descricao}
                </CardDescription>
              </CardHeader>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <Award className="w-5 h-5" />
                  Competências Requeridas
                </CardTitle>
                <CardDescription>
                  Habilidades necessárias para esta carreira
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {carreira.competenciasRequeridas.map((comp) => (
                    <div
                      key={comp.id}
                      className="p-4 bg-gray-50 rounded-lg border border-gray-200"
                    >
                      <h4 className="font-semibold text-gray-900 mb-2">
                        {comp.nome}
                      </h4>
                      <p className="text-sm text-gray-600">{comp.descricao}</p>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>

          <div className="space-y-6">
            <Card>
              <CardHeader>
                <CardTitle>Criar Trilha de Aprendizado</CardTitle>
                <CardDescription>
                  Crie uma trilha personalizada para esta carreira
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                <p className="text-sm text-gray-600">
                  Ao criar uma trilha, você receberá recomendações de cursos
                  personalizadas e explicações geradas por IA para guiar seu
                  desenvolvimento profissional.
                </p>
                <Button
                  className="w-full"
                  onClick={handleCriarTrilha}
                  disabled={criandoTrilha}
                >
                  {criandoTrilha ? (
                    <>
                      <Loader2 className="w-4 h-4 mr-2 animate-spin" />
                      Criando...
                    </>
                  ) : (
                    "Criar Minha Trilha"
                  )}
                </Button>
              </CardContent>
            </Card>

            <Card className="bg-gradient-to-br from-emerald-50 to-teal-50 border-emerald-200">
              <CardHeader>
                <CardTitle className="text-emerald-900">
                  Sobre Energia Sustentável
                </CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-emerald-800">
                  O setor de energia sustentável está em rápido crescimento,
                  oferecendo oportunidades únicas para profissionais que desejam
                  fazer a diferença no futuro do planeta.
                </p>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
}
