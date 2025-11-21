import { Link, useLocation } from "wouter";
import { Button } from "@/components/ui/button";
import { useAuth } from "@/contexts/AuthContext";
import { Menu, X, Zap } from "lucide-react";
import { useState } from "react";

export default function Navbar() {
  const [, navigate] = useLocation();
  const { isAuthenticated, user, logout } = useAuth();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <nav className="bg-white border-b border-gray-200 sticky top-0 z-50">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link href="/">
            <a className="flex items-center gap-2 text-xl font-bold text-emerald-600 hover:text-emerald-700">
              <Zap className="w-6 h-6" />
              CareerMap
            </a>
          </Link>

          {/* Desktop Menu */}
          <div className="hidden md:flex items-center gap-6">
            <Link href="/carreiras">
              <a className="text-gray-700 hover:text-emerald-600 transition-colors">
                Carreiras
              </a>
            </Link>
            {isAuthenticated && (
              <>
                <Link href="/dashboard">
                  <a className="text-gray-700 hover:text-emerald-600 transition-colors">
                    Dashboard
                  </a>
                </Link>
                <Link href="/trilhas">
                  <a className="text-gray-700 hover:text-emerald-600 transition-colors">
                    Minhas Trilhas
                  </a>
                </Link>
              </>
            )}
          </div>

          {/* Auth Buttons */}
          <div className="hidden md:flex items-center gap-3">
            {isAuthenticated ? (
              <>
                <span className="text-sm text-gray-600">Olá, {user?.nome}</span>
                <Button variant="outline" onClick={handleLogout}>
                  Sair
                </Button>
              </>
            ) : (
              <>
                <Link href="/login">
                  <Button variant="ghost">Entrar</Button>
                </Link>
                <Link href="/register">
                  <Button>Cadastrar</Button>
                </Link>
              </>
            )}
          </div>

          {/* Mobile Menu Button */}
          <button
            className="md:hidden"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            {mobileMenuOpen ? <X /> : <Menu />}
          </button>
        </div>

        {/* Mobile Menu */}
        {mobileMenuOpen && (
          <div className="md:hidden py-4 border-t">
            <div className="flex flex-col gap-4">
              <Link href="/carreiras">
                <a className="text-gray-700 hover:text-emerald-600 transition-colors">
                  Carreiras
                </a>
              </Link>
              {isAuthenticated && (
                <>
                  <Link href="/dashboard">
                    <a className="text-gray-700 hover:text-emerald-600 transition-colors">
                      Dashboard
                    </a>
                  </Link>
                  <Link href="/trilhas">
                    <a className="text-gray-700 hover:text-emerald-600 transition-colors">
                      Minhas Trilhas
                    </a>
                  </Link>
                </>
              )}
              {isAuthenticated ? (
                <>
                  <span className="text-sm text-gray-600">Olá, {user?.nome}</span>
                  <Button variant="outline" onClick={handleLogout} className="w-full">
                    Sair
                  </Button>
                </>
              ) : (
                <>
                  <Link href="/login">
                    <Button variant="ghost" className="w-full">Entrar</Button>
                  </Link>
                  <Link href="/register">
                    <Button className="w-full">Cadastrar</Button>
                  </Link>
                </>
              )}
            </div>
          </div>
        )}
      </div>
    </nav>
  );
}
