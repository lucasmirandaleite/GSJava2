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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponseDTO(token, "Bearer"));
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody @Valid UsuarioRegistrationDTO registrationDTO) {
        Usuario novoUsuario = usuarioService.registrarNovoUsuario(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}
