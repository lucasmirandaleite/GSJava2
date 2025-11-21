package com.fiap.careermap.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "{login.email.notblank}")
    @Email(message = "{login.email.invalid}")
    private String email;

    @NotBlank(message = "{login.senha.notblank}")
    private String senha;
}
