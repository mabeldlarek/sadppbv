package com.belarek.SADPPBV.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private GetUserDTO usuario;
    private String message;
    private boolean sucess;
    public UsuarioResponseDTO(GetUserDTO usuario, ResponseDTO responseDTO) {
        this.usuario = usuario;
        this.message = responseDTO.getMessage();
        this.sucess = responseDTO.isSucess();
    }

}
