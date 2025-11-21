import { Button } from "@/components/ui/button";
import { Link } from "wouter";
import Navbar from "@/components/Navbar";
import { Zap, TrendingUp, Award, Lightbulb, ArrowRight, CheckCircle2 } from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";

export default function Home() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-b from-emerald-50 to-white">
      <Navbar />
      
      {/* Hero Section */}
      <section className="container py-20 md:py-32">
        <div className="grid md:grid-cols-2 gap-12 items-center">
          <div className="space-y-6">
            <div className="inline-block px-4 py-2 bg-emerald-100 text-emerald-700 rounded-full text-sm font-medium">
              Energia Sustentável • Futuro Profissional
            </div>
            <h1 className="text-4xl md:text-6xl font-bold text-gray-900 leading-tight">
              Construa sua carreira em{" "}
              <span className="text-emerald-600">Energia Sustentável</span>
            </h1>
            <p className="text-lg text-gray-600">
              Descubra oportunidades de carreira alinhadas com o futuro da energia limpa.
              Planeje seu desenvolvimento profissional com trilhas personalizadas e
              recomendações inteligentes.
            </p>
            <div className="flex gap-4">
              {isAuthenticated ? (
                <Link href="/dashboard">
                  <Button size="lg" className="gap-2">
                    Ir para Dashboard <ArrowRight className="w-4 h-4" />
                  </Button>
                </Link>
              ) : (
                <>
                  <Link href="/register">
                    <Button size="lg" className="gap-2">
                      Começar Agora <ArrowRight className="w-4 h-4" />
                    </Button>
                  </Link>
                  <Link href="/carreiras">
                    <Button size="lg" variant="outline">
                      Explorar Carreiras
                    </Button>
                  </Link>
                </>
              )}
            </div>
          </div>
          <div className="relative">
            <img
              src="/hero-renewable.jpg"
              alt="Profissionais de energia renovável"
              className="rounded-2xl shadow-2xl"
            />
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="container py-20 bg-white">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            Como o CareerMap funciona
          </h2>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Uma plataforma completa para planejar e desenvolver sua carreira no setor de
            energia sustentável
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-8">
          <div className="bg-gradient-to-br from-emerald-50 to-teal-50 p-8 rounded-xl">
            <div className="w-12 h-12 bg-emerald-600 rounded-lg flex items-center justify-center mb-4">
              <TrendingUp className="w-6 h-6 text-white" />
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-3">
              Mapeamento de Carreiras
            </h3>
            <p className="text-gray-600">
              Explore diversas carreiras no setor de energia sustentável e descubra qual
              se alinha melhor com seus objetivos e competências.
            </p>
          </div>

          <div className="bg-gradient-to-br from-blue-50 to-cyan-50 p-8 rounded-xl">
            <div className="w-12 h-12 bg-blue-600 rounded-lg flex items-center justify-center mb-4">
              <Award className="w-6 h-6 text-white" />
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-3">
              Análise de Competências
            </h3>
            <p className="text-gray-600">
              Avalie suas competências atuais e descubra quais habilidades você precisa
              desenvolver para alcançar seus objetivos profissionais.
            </p>
          </div>

          <div className="bg-gradient-to-br from-purple-50 to-pink-50 p-8 rounded-xl">
            <div className="w-12 h-12 bg-purple-600 rounded-lg flex items-center justify-center mb-4">
              <Lightbulb className="w-6 h-6 text-white" />
            </div>
            <h3 className="text-xl font-bold text-gray-900 mb-3">
              Trilhas Personalizadas
            </h3>
            <p className="text-gray-600">
              Receba recomendações de cursos e trilhas de aprendizado personalizadas,
              com explicações geradas por IA para guiar seu desenvolvimento.
            </p>
          </div>
        </div>
      </section>

      {/* Benefits Section */}
      <section className="container py-20">
        <div className="grid md:grid-cols-2 gap-12 items-center">
          <div>
            <img
              src="/sustainable-tech.jpg"
              alt="Tecnologias sustentáveis"
              className="rounded-2xl shadow-xl"
            />
          </div>
          <div className="space-y-6">
            <h2 className="text-3xl md:text-4xl font-bold text-gray-900">
              Por que escolher o CareerMap?
            </h2>
            <div className="space-y-4">
              <div className="flex gap-3">
                <CheckCircle2 className="w-6 h-6 text-emerald-600 flex-shrink-0" />
                <div>
                  <h4 className="font-semibold text-gray-900 mb-1">
                    Foco em Sustentabilidade
                  </h4>
                  <p className="text-gray-600">
                    Todas as carreiras e cursos são voltados para o setor de energia
                    sustentável e renovável.
                  </p>
                </div>
              </div>
              <div className="flex gap-3">
                <CheckCircle2 className="w-6 h-6 text-emerald-600 flex-shrink-0" />
                <div>
                  <h4 className="font-semibold text-gray-900 mb-1">
                    Recomendações Inteligentes
                  </h4>
                  <p className="text-gray-600">
                    Algoritmos avançados analisam seu perfil e sugerem as melhores
                    oportunidades de desenvolvimento.
                  </p>
                </div>
              </div>
              <div className="flex gap-3">
                <CheckCircle2 className="w-6 h-6 text-emerald-600 flex-shrink-0" />
                <div>
                  <h4 className="font-semibold text-gray-900 mb-1">
                    Suporte de IA
                  </h4>
                  <p className="text-gray-600">
                    Explicações detalhadas geradas por inteligência artificial para cada
                    trilha de aprendizado.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="container py-20">
        <div className="bg-gradient-to-r from-emerald-600 to-teal-600 rounded-3xl p-12 md:p-16 text-center text-white">
          <Zap className="w-16 h-16 mx-auto mb-6" />
          <h2 className="text-3xl md:text-4xl font-bold mb-4">
            Pronto para transformar sua carreira?
          </h2>
          <p className="text-lg mb-8 text-emerald-50 max-w-2xl mx-auto">
            Junte-se a centenas de profissionais que já estão construindo carreiras de
            sucesso no setor de energia sustentável.
          </p>
          {!isAuthenticated && (
            <Link href="/register">
              <Button size="lg" variant="secondary" className="gap-2">
                Criar Conta Gratuita <ArrowRight className="w-4 h-4" />
              </Button>
            </Link>
          )}
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-12">
        <div className="container">
          <div className="grid md:grid-cols-3 gap-8">
            <div>
              <div className="flex items-center gap-2 text-white font-bold text-lg mb-4">
                <Zap className="w-5 h-5 text-emerald-500" />
                CareerMap
              </div>
              <p className="text-sm">
                Planejamento de carreira para o futuro da energia sustentável.
              </p>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">Links Rápidos</h4>
              <div className="space-y-2 text-sm">
                <div><Link href="/carreiras"><a className="hover:text-white">Carreiras</a></Link></div>
                <div><Link href="/login"><a className="hover:text-white">Entrar</a></Link></div>
                <div><Link href="/register"><a className="hover:text-white">Cadastrar</a></Link></div>
              </div>
            </div>
            <div>
              <h4 className="text-white font-semibold mb-4">Sobre</h4>
              <p className="text-sm">
                Projeto desenvolvido para a Global Solution 2025/2 - FIAP
              </p>
            </div>
          </div>
          <div className="border-t border-gray-800 mt-8 pt-8 text-center text-sm">
            © 2025 CareerMap. Todos os direitos reservados.
          </div>
        </div>
      </footer>
    </div>
  );
}
