package com.belarek.SADPPBV.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    private String nome;
    @NotBlank(message = "Invalid password: Empty password")
    @NotNull(message = "Invalid password: password is NULL")
    private String senha;
    @NotBlank(message = "Invalid email: Empty Email")
    @NotNull(message = "Invalid email: Email is NULL")
    private String email;
    @Min(value = 0, message = "Registro deve ser informado")
    private int registro;
    @Min(value = 1, message = "Tipo de usuário deve ser informado")
    private int tipo_usuario;
}
