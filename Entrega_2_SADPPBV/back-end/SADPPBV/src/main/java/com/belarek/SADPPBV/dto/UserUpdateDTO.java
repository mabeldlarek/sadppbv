package com.belarek.SADPPBV.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDTO {
    private String nome;
    private String senha;
    private String email;
}
