package com.belarek.SADPPBV.dto.usuarios;

import com.belarek.SADPPBV.dto.ResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ListaUsuariosResponseDTO {
    private List<GetUserDTO> usuarios;
    private String message;
    private boolean success;

    public ListaUsuariosResponseDTO(List<GetUserDTO> usuarios, ResponseDTO responseDTO) {
        this.usuarios = usuarios;
        this.message = responseDTO.getMessage();
        this.success = responseDTO.isSuccess();
    }
}
