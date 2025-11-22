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
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    // ===================== REGISTRO =====================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioRegistrationDTO usuarioDTO) {
        try {
            // Verifica se usuário já existe
            if (usuarioService.findByEmail(usuarioDTO.getEmail()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }
            
            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(usuarioDTO.getSenha());
            
            usuarioService.save(usuario);
            return ResponseEntity.ok("Usuário cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // ===================== FORM DE REGISTRO (OPCIONAL) =====================
    @GetMapping("/register")
    public ResponseEntity<String> registerForm() {
        // Mantenha o HTML atual se quiser
        String html = "<!DOCTYPE html>\n" +
                // ... seu HTML atual
                "</html>";
        return ResponseEntity.ok().body(html);
    }
}
