package com.belarek.SADPPBV.dto.segmentos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PutSegmentoDTO {
    @NotNull(message = "Invalid Distancia: Distancia is NULL")
    private double distancia;
    @NotNull(message = "Invalid Ponto Inicial is NULL")
    private int ponto_inicial;
    @NotNull(message = "Invalid Ponto Final is NULL")
    private int ponto_final;
    @NotNull(message = "Invalid Status is NULL")
    @Min(value = 0, message = "Status deve ser igual a 0 ou 1")
    @Max(value = 1, message = "Status deve ser igual a 0 ou 1")
    private int status;
    @NotBlank(message = "Invalid Direção: Empty Direção")
    @NotNull(message = "Invalid Direção is NULL")
    private String direcao;
}
