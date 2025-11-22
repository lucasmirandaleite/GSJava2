package com.fiap.careermap.controller;

import com.fiap.careermap.dto.LoginDTO;
import com.fiap.careermap.dto.TokenDTO;
import com.fiap.careermap.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());
            
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = tokenService.gerarToken(userDetails);
            
            return ResponseEntity.ok(new TokenDTO(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is healthy");
    }
}
