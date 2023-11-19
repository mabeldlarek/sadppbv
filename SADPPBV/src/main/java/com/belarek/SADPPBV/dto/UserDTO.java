package com.belarek.SADPPBV.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Max(value = 50, message = "Nome deve ter até 100 caracteres")
    private String nome;
    @NotBlank(message = "Invalid password: Empty password")
    @NotNull(message = "Invalid password: password is NULL")
    @Max(value = 50, message = "Senha deve ter até 50 caracteres")
    private String senha;
    @NotBlank(message = "Invalid email: Empty Email")
    @NotNull(message = "Invalid email: Email is NULL")
    @Max(value = 50, message = "Email deve ter até 50 caracteres")
    private String email;
    @Min(value = 0, message = "Registro deve ser informado")
    private int registro;
    @Min(value = 0, message = "Tipo de usuário deve ser informado")
    private int tipo_usuario;
}
