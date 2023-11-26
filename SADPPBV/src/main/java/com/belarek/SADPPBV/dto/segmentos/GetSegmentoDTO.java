package com.belarek.SADPPBV.dto.segmentos;

import com.belarek.SADPPBV.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GetSegmentoDTO {
    private SegmentoDTO segmento;
    private String message;
    private boolean success;

    public GetSegmentoDTO(SegmentoDTO segmento, ResponseDTO response) {
        this.segmento = segmento;
        this.message = response.getMessage();
        this.success = response.isSuccess();
    }
}
