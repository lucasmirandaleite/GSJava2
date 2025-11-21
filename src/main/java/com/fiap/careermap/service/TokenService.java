package com.fiap.careermap.service;

import com.fiap.careermap.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setIssuer("careermap-api")
                .setSubject(usuario.getEmail())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(2 * 60 * 60))) // 2h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 📌 NOVO: verifica validade sem quebrar a API
    public boolean isTokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false; // token inválido, expirado, malformado, etc
        }
    }

    public String getSubject(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado!");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Token inválido!");
        } catch (SignatureException e) {
            throw new RuntimeException("Assinatura inválida do token!");
        }
    }
}
