package com.belarek.SADPPBV.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pontos")
public class Ponto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ponto_id;
    private String nome;
}
