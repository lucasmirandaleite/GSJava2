package com.fiap.careermap.controller;

import com.fiap.careermap.dto.UsuarioDTO;
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

    // Buscar perfil autenticado (exibe só dados essenciais, use DTO por segurança)
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> getPerfil(@AuthenticationPrincipal Usuario usuario) {
        // Aqui converte o objeto da entidade para o DTO, que só devolve dados necessários
        UsuarioDTO dto = new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
        return ResponseEntity.ok(dto);
    }

    // Atualizar perfil autenticado (senha OPCIONAL)
    @PutMapping("/perfil")
    public ResponseEntity<UsuarioDTO> atualizarPerfil(
            @AuthenticationPrincipal Usuario usuario,
            @RequestBody @Valid UsuarioRegistrationDTO dto) {

        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario.getId(), dto);
        UsuarioDTO retorno = new UsuarioDTO(
            usuarioAtualizado.getId(),
            usuarioAtualizado.getNome(),
            usuarioAtualizado.getEmail(),
            usuarioAtualizado.getRole()
        );
        return ResponseEntity.ok(retorno);
    }

    // Recuperar senha (envio de e-mail - só um placeholder)
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody String email) {
        // Aqui você implementaria lógica para envio de e-mail
        return ResponseEntity.ok("Se o email estiver cadastrado, um link de recuperação de senha será enviado.");
    }
}
