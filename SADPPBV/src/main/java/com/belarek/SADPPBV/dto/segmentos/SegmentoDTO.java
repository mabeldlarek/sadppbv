package com.belarek.SADPPBV.dto.segmentos;

import lombok.Data;

@Data
public class SegmentoDTO {
    private long segmento_id;
    private double distancia;
    private String ponto_inicial;
    private String ponto_final;
    private int status;
    private String direcao;
}
