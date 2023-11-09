package com.belarek.SADPPBV.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ListaUsuariosResponseDTO {
    private List<UserDTO> usuarios;
    private String message;
    private boolean sucess;

    public ListaUsuariosResponseDTO(List<UserDTO> usuarios, ResponseDTO responseDTO) {
        this.usuarios = usuarios;
        this.message = responseDTO.getMessage();
        this.sucess = responseDTO.isSucess();
    }
}
