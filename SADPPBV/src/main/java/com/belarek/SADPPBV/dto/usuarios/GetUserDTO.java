package com.belarek.SADPPBV.dto.usuarios;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetUserDTO {
    private String nome;
    private String email;
    private int registro;
    private int tipo_usuario;
}
