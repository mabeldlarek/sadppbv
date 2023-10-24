package com.belarek.SADPPBV.dto;

import lombok.Data;

@Data
public class SegmentoDTO {
    private long segmento_id;
    private double distancia;
    private int ponto_inicial;
    private int ponto_final;
    private int status;
    private String direcao;
}
