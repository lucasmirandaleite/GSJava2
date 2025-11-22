package com.fiap.careermap.config;

import com.fiap.careermap.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // ✅ Ignora o filtro para endpoints públicos
        return path.startsWith("/auth/") || path.startsWith("/actuator/") || path.startsWith("/api/v1/trilhas/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("🔒 SecurityFilter chamado para: " + request.getRequestURI());

        // Lógica de autentração alternativa (sem tokens)
        // Por exemplo: autentração básica, sessões, ou outro método
        String credentials = recuperarCredenciais(request);

        try {
            if (credentials != null) {
                // Aqui você pode implementar sua lógica de autentração alternativa
                // Por exemplo: autentração básica, API key, etc.
                UserDetails usuario = autenticarComCredenciais(credentials);
                
                if (usuario != null) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    usuario, null, usuario.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Credenciais inválidas\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarCredenciais(HttpServletRequest request) {
        // Método alternativo para recuperar credenciais
        // Por exemplo: Header personalizado, parâmetro de query, etc.
        String apiKey = request.getHeader("X-API-Key");
        String basicAuth = request.getHeader("Authorization");
        
        if (apiKey != null) {
            return apiKey;
        }
        
        if (basicAuth != null && basicAuth.startsWith("Basic ")) {
            return basicAuth;
        }
        
        return null;
    }

    private UserDetails autenticarComCredenciais(String credentials) {
        // Implemente sua lógica de autentração alternativa aqui
        // Por exemplo: validar API key, autentração básica, etc.
        
        // Exemplo básico - você precisará adaptar para sua lógica
        try {
            // Se for API Key
            if (credentials.length() == 32) { // Exemplo: API Key de 32 caracteres
                return usuarioService.loadUserByApiKey(credentials);
            }
            
            // Se for Basic Auth
            if (credentials.startsWith("Basic ")) {
                // Decodificar Base64 e extrair username/password
                // String decoded = new String(Base64.getDecoder().decode(credentials.substring(6)));
                // String[] parts = decoded.split(":");
                // return usuarioService.loadUserByUsername(parts[0]);
            }
        } catch (Exception e) {
            // Credenciais inválidas
        }
        
        return null;
    }
}
