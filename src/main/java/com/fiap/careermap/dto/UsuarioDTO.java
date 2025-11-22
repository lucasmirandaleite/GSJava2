package com.fiap.careermap.dto;

import com.fiap.careermap.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String username;
    private Role role;  // ← Mudou de String para Role
}
