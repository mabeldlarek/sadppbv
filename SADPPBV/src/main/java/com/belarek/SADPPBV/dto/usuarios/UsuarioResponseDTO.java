package com.belarek.SADPPBV.dto.usuarios;


import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.dto.usuarios.GetUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private GetUserDTO usuario;
    private String message;
    private boolean success;
    public UsuarioResponseDTO(GetUserDTO usuario, ResponseDTO responseDTO) {
        this.usuario = usuario;
        this.message = responseDTO.getMessage();
        this.success = responseDTO.isSuccess();
    }

}
