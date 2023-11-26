package com.belarek.SADPPBV.dto.pontos;

import com.belarek.SADPPBV.dto.ResponseDTO;

public class GetPontoDTO {
    private PontoDTO ponto;
    private String message;
    private boolean success;

    public GetPontoDTO(PontoDTO ponto, ResponseDTO response) {
        this.ponto = ponto;
        this.message = response.getMessage();
        this.success = response.isSuccess();
    }
}
