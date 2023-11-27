package com.belarek.SADPPBV.dto.segmentos;

import lombok.Data;

@Data
public class PutSegmentoDTO {
    private double distancia;
    private int ponto_inicial;
    private int ponto_final;
    private int status;
    private String direcao;
}
