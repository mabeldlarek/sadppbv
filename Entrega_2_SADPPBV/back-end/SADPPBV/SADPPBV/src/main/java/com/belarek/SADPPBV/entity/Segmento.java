package com.belarek.SADPPBV.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "segmento")
public class Segmento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long segmento_id;
    private double distancia;
    private int ponto_inicial;
    private int ponto_final;
    private int status;
    private String direcao;
}
