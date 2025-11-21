package com.fiap.careermap.controller;

import com.fiap.careermap.dto.UsuarioRegistrationDTO;
import com.fiap.careermap.model.Usuario;
import com.fiap.careermap.service.UsuarioService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<Usuario> getPerfil(@AuthenticationPrincipal Usuario usuario) {
        // O objeto Usuario é injetado diretamente do contexto de segurança
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/me")
    public ResponseEntity<Usuario> atualizarPerfil(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid UsuarioRegistrationDTO dto) {
        // O DTO de registro é reutilizado para atualização, mas a senha é opcional
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario.getId(), dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Placeholder para Recuperação de Senha (requer infraestrutura de e-mail)
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody String email) {
        // Lógica de envio de e-mail para recuperação de senha
        return ResponseEntity.ok("Se o email estiver cadastrado, um link de recuperação de senha será enviado.");
    }
}
