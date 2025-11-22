package com.fiap.careermap.controller;

import com.fiap.careermap.dto.CadastroDTO;
import com.fiap.careermap.dto.LoginDTO;
import com.fiap.careermap.dto.UsuarioDTO;
import com.fiap.careermap.model.Role;
import com.fiap.careermap.model.Usuario;
import com.fiap.careermap.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint de cadastro
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody CadastroDTO cadastroDTO) {
        try {
            // Verificar se o email já existe
            if (usuarioRepository.findByEmail(cadastroDTO.getEmail()).isPresent()) {
                Map<String, String> erro = new HashMap<>();
                erro.put("erro", "Email já cadastrado");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
            }

            // Criar novo usuário
            Usuario usuario = new Usuario();
            usuario.setNome(cadastroDTO.getNome());
            usuario.setEmail(cadastroDTO.getEmail());
            usuario.setSenha(passwordEncoder.encode(cadastroDTO.getSenha()));
            usuario.setRole(cadastroDTO.getRole() != null ? cadastroDTO.getRole() : Role.USER);

            // Salvar no banco
            Usuario usuarioSalvo = usuarioRepository.save(usuario);

            // Retornar resposta de sucesso
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuarioSalvo.getId());
            usuarioDTO.setEmail(usuarioSalvo.getEmail());
            usuarioDTO.setUsername(usuarioSalvo.getNome());
            usuarioDTO.setRole(usuarioSalvo.getRole());

            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Usuário cadastrado com sucesso!");
            resposta.put("usuario", usuarioDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);

        } catch (Exception e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Erro ao cadastrar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }

    /**
     * Endpoint de login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // Buscar usuário por email
            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginDTO.getEmail());

            if (usuarioOpt.isEmpty()) {
                Map<String, String> erro = new HashMap<>();
                erro.put("erro", "Email ou senha inválidos");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
            }

            Usuario usuario = usuarioOpt.get();

            // Verificar senha
            if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
                Map<String, String> erro = new HashMap<>();
                erro.put("erro", "Email ou senha inválidos");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
            }

            // Login com sucesso - retornar dados do usuário
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setUsername(usuario.getNome());
            usuarioDTO.setRole(usuario.getRole());

            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Login realizado com sucesso!");
            resposta.put("usuario", usuarioDTO);

            return ResponseEntity.ok(resposta);

        } catch (Exception e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Erro ao fazer login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is healthy");
    }
}
