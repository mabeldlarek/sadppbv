package com.belarek.SADPPBV.dto.pontos;

import com.belarek.SADPPBV.dto.ResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ListPontoDTO {
    private List<PontoDTO> pontos;
    private String message;
    private boolean success;

    public ListPontoDTO(List<PontoDTO> pontos, ResponseDTO response) {
        this.pontos = pontos;
        this.message = response.getMessage();
        this.success = response.isSuccess();;
    }
}
