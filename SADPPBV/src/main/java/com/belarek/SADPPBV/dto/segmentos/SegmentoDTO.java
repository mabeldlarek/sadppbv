package com.belarek.SADPPBV.dto.segmentos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedNotifications;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegmentoDTO {
    private long segmento_id;
    private double distancia;
    private String ponto_inicial;
    private String ponto_final;
    private int status;
    private String direcao;
}
