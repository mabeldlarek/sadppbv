package com.belarek.SADPPBV.dto;

import lombok.Data;

@Data
public class AuthTokenDTO {
    private String token;
    private Long userId;
    private boolean isValido;

    public AuthTokenDTO(String token, Long userId, boolean isValido) {
        this.token = token;
        this.userId = userId;
        this.isValido = isValido;
    }
}
