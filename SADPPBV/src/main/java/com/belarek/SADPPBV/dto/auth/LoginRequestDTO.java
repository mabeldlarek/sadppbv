package com.belarek.SADPPBV.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String senha;
    private int registro;
}
