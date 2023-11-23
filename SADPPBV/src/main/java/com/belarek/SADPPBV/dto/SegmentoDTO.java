package com.belarek.SADPPBV.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SegmentoDTO {
    @NotNull(message = "Invalid distancia: Distance is NULL")
    private Double distancia;
    @NotNull(message = "Invalid ponto_inicial: Initial point is NULL")
    private Integer ponto_inicial;
    @NotNull(message = "Invalid ponto_final: End point is NULL")
    private Integer ponto_final;
    @NotNull(message = "Invalid status: Status is NULL")
    private Integer status;
    @NotBlank(message = "Invalid direcao: Empty direction")
    @NotNull(message = "Invalid direcao: direcao is NULL")
    private String direcao;
}
