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
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ponto_inicial", referencedColumnName = "ponto_id")
    private Ponto ponto_inicial;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ponto_final", referencedColumnName = "ponto_id")
    private Ponto ponto_final;
    private int status;
    private String direcao;
}
