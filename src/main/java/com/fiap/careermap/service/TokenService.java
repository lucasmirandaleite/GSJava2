package com.fiap.careermap.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.MalformedJwtException;
import com.fiap.careermap.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setIssuer("careermap-api")
                .setSubject(usuario.getEmail())
                .setExpiration(dataExpiracao())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getSubject(String tokenJWT) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(tokenJWT)
                    .getBody()
                    .getSubject();
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private java.util.Date dataExpiracao() {
        return java.util.Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
    }
}
