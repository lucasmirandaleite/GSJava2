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
@CrossOrigin(origins = "*", allowedHeaders = "*")  // Habilita requisições de qualquer origem
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

    // ===================== FORM DE REGISTRO (HTML, OPCIONAL) =====================
    @GetMapping("/register")
    public ResponseEntity<String> registerForm() {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"pt-BR\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Registrar Usuário</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial; background: #f4f4f4; padding: 20px; }\n" +
                "        .card {\n" +
                "            max-width: 400px;\n" +
                "            margin: auto;\n" +
                "            background: #fff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\n" +
                "        }\n" +
                "        input, button {\n" +
                "            width: 100%;\n" +
                "            padding: 10px;\n" +
                "            margin-top: 10px;\n" +
                "            border-radius: 5px;\n" +
                "            border: 1px solid #ccc;\n" +
                "        }\n" +
                "        button {\n" +
                "            background: #4CAF50;\n" +
                "            color: white;\n" +
                "            font-weight: bold;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"card\">\n" +
                "        <h2>Registrar Usuário</h2>\n" +
                "        <form method=\"POST\" action=\"/auth/register\">\n" +
                "            <input type=\"text\" name=\"nome\" placeholder=\"Nome\" required>\n" +
                "            <input type=\"email\" name=\"email\" placeholder=\"Email\" required>\n" +
                "            <input type=\"password\" name=\"senha\" placeholder=\"Senha\" required>\n" +
                "            <button type=\"submit\">Registrar</button>\n" +
                "        </form>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        return ResponseEntity.ok().body(html);
    }

    // ===================== POST REGISTRO VIA JSON (CORRIGIDO) =====================
   @PostMapping("/register")
public ResponseEntity<Usuario> register(@RequestBody @Valid UsuarioRegistrationDTO registrationDTO) {
    System.out.println("CHAMOU O ENDPOINT DE REGISTRO: " + registrationDTO.getEmail());
    Usuario novoUsuario = usuarioService.registrarNovoUsuario(registrationDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
}

}
