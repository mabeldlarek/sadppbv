package com.belarek.SADPPBV.dto;

import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;

public class Node {
    public final String ponto;
    public final Node anterior;
    public final SegmentoDTO segmento;
    public final double distancia;

    public Node(String ponto, Node anterior, double distancia) {
        this.ponto = ponto;
        this.anterior = anterior;
        this.distancia = distancia;
        this.segmento = anterior != null ? anterior.segmento : null;
    }
}
