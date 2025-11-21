package com.fiap.careermap.controller;

import com.fiap.careermap.dto.LoginDTO;
import com.fiap.careermap.dto.TokenResponseDTO;
import com.fiap.careermap.dto.UsuarioRegistrationDTO;
import com.fiap.careermap.model.Usuario;
import com.fiap.careermap.service.TokenService;
import com.fiap.careermap.service.UsuarioService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponseDTO(token, "Bearer"));
    }

    // ===================== FORM DE REGISTRO =====================
    @GetMapping("/register")
    public ResponseEntity<String> registerForm() {
        String html = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Registrar Usuário</title>
                    <style>
                        body { font-family: Arial; background: #f4f4f4; padding: 20px; }
                        .card {
                            max-width: 400px;
                            margin: auto;
                            background: #fff;
                            padding: 20px;
                            border-radius: 10px;
                            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        }
                        input, button {
                            width: 100%%;
                            padding: 10px;
                            margin-top: 10px;
                            border-radius: 5px;
                            border: 1px solid #ccc;
                        }
                        button {
                            background: #4CAF50;
                            color: white;
                            font-weight: bold;
                            cursor: pointer;
                        }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <h2>Registrar Usuário</h2>
                        <form method="POST" action="/auth/register">
                            <input type="text" name="nome" placeholder="Nome" required>
                            <input type="email" name="email" placeholder="Email" required>
                            <input type="password" name="senha" placeholder="Senha" required>
                            <button type="submit">Registrar</button>
                        </form>
                    </div>
                </body>
                </html>
                """;

        return ResponseEntity.ok().body(html);
    }

    // ===================== POST REGISTRO VIA FORM =====================
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@ModelAttribute UsuarioRegistrationDTO registrationDTO) {
        Usuario novoUsuario = usuarioService.registrarNovoUsuario(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}
