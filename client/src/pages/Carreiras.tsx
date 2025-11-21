import { useEffect, useState } from "react";
import { Link } from "wouter";
import Navbar from "@/components/Navbar";
import { api, Carreira, PageResponse } from "@/lib/api";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ArrowRight, Briefcase, Loader2 } from "lucide-react";
import { toast } from "sonner";

export default function Carreiras() {
  const [carreiras, setCarreiras] = useState<PageResponse<Carreira> | null>(null);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);

  useEffect(() => {
    loadCarreiras();
  }, [page]);

  const loadCarreiras = async () => {
    try {
      setLoading(true);
      const data = await api.getCarreiras(page, 9);
      setCarreiras(data);
    } catch (error: any) {
      toast.error(error.message || "Erro ao carregar carreiras");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <div className="container py-12">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">
            Explore Carreiras em Energia Sustentável
          </h1>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Descubra oportunidades profissionais alinhadas com o futuro da energia limpa
            e renovável
          </p>
        </div>

        {loading ? (
          <div className="flex justify-center items-center py-20">
            <Loader2 className="w-8 h-8 animate-spin text-emerald-600" />
          </div>
        ) : carreiras && carreiras.content.length > 0 ? (
          <>
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
              {carreiras.content.map((carreira) => (
                <Card key={carreira.id} className="hover:shadow-lg transition-shadow">
                  <CardHeader>
                    <div className="w-12 h-12 bg-emerald-100 rounded-lg flex items-center justify-center mb-3">
                      <Briefcase className="w-6 h-6 text-emerald-600" />
                    </div>
                    <CardTitle>{carreira.nome}</CardTitle>
                    <CardDescription className="line-clamp-3">
                      {carreira.descricao}
                    </CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="mb-4">
                      <p className="text-sm text-gray-600 mb-2">
                        Competências requeridas:
                      </p>
                      <div className="flex flex-wrap gap-2">
                        {carreira.competenciasRequeridas.slice(0, 3).map((comp) => (
                          <span
                            key={comp.id}
                            className="px-2 py-1 bg-emerald-50 text-emerald-700 text-xs rounded-full"
                          >
                            {comp.nome}
                          </span>
                        ))}
                        {carreira.competenciasRequeridas.length > 3 && (
                          <span className="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded-full">
                            +{carreira.competenciasRequeridas.length - 3}
                          </span>
                        )}
                      </div>
                    </div>
                    <Link href={`/carreiras/${carreira.id}`}>
                      <Button className="w-full gap-2">
                        Ver Detalhes <ArrowRight className="w-4 h-4" />
                      </Button>
                    </Link>
                  </CardContent>
                </Card>
              ))}
            </div>

            {/* Paginação */}
            {carreiras.totalPages > 1 && (
              <div className="flex justify-center gap-2">
                <Button
                  variant="outline"
                  disabled={page === 0}
                  onClick={() => setPage(page - 1)}
                >
                  Anterior
                </Button>
                <span className="flex items-center px-4 text-sm text-gray-600">
                  Página {page + 1} de {carreiras.totalPages}
                </span>
                <Button
                  variant="outline"
                  disabled={page >= carreiras.totalPages - 1}
                  onClick={() => setPage(page + 1)}
                >
                  Próxima
                </Button>
              </div>
            )}
          </>
        ) : (
          <div className="text-center py-20">
            <Briefcase className="w-16 h-16 text-gray-400 mx-auto mb-4" />
            <h3 className="text-xl font-semibold text-gray-900 mb-2">
              Nenhuma carreira encontrada
            </h3>
            <p className="text-gray-600">
              Não há carreiras cadastradas no momento.
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
