package com.belarek.SADPPBV.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private int registro;
    private String token;
    private String message;
    private boolean success;
}
