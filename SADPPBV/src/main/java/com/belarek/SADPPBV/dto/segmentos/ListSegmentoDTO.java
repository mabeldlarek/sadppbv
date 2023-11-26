package com.belarek.SADPPBV.dto.segmentos;

import com.belarek.SADPPBV.dto.ResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ListSegmentoDTO {
    private List<SegmentoDTO> segmentos;
    private String message;
    private boolean success;

    public ListSegmentoDTO(List<SegmentoDTO> segmentos, ResponseDTO response) {
        this.segmentos = segmentos;
        this.message = response.getMessage();
        this.success = response.isSuccess();
    }
}
