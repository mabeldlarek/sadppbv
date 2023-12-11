package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.Node;
import com.belarek.SADPPBV.dto.RotaCalculadaDTO;
import com.belarek.SADPPBV.dto.RotaDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;
import com.belarek.SADPPBV.service.RotaService;
import com.belarek.SADPPBV.service.SegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RotaServiceImpl implements RotaService {
    private List<SegmentoDTO> segmentos;
    @Autowired
    private SegmentoService segmentoService;
    private List<SegmentoDTO> melhorRota;
    private double menorDistancia;

    @Override
    public RotaCalculadaDTO getRota(RotaDTO rotaDTO) {
       List<SegmentoDTO> segmentosEncontrados = calcularRota(rotaDTO.getOrigem(), rotaDTO.getDestino());
       return mapToDTO(segmentosEncontrados);
    }

    private List<SegmentoDTO> getSegmentos() {
        return segmentoService.listSegmentos();
    }

    @Override
    public List<SegmentoDTO> calcularRota(String pontoInicial, String pontoFinal) {
        segmentos = getSegmentos();
        return encontrarMelhorRota(pontoInicial, pontoFinal, segmentos);
    }

    private RotaCalculadaDTO mapToDTO(List<SegmentoDTO> segmentosEncontrados) {
        if (segmentosEncontrados.isEmpty() || segmentosEncontrados == null) {
            return null;
        }
        RotaCalculadaDTO rotaCalculadaDTO = new RotaCalculadaDTO();
        rotaCalculadaDTO.setRota(segmentosEncontrados);
        rotaCalculadaDTO.setSucess(false);
        rotaCalculadaDTO.setMessage("Rota calculada com sucesso");
        return rotaCalculadaDTO;
    }

    public List<SegmentoDTO> encontrarMelhorRota(String pontoInicial, String pontoFinal, List<SegmentoDTO> segmentos) {
        melhorRota = new ArrayList<>();
        menorDistancia = Double.POSITIVE_INFINITY;

        Map<String, Double> distanciaMinima = new HashMap<>();
        Map<String, SegmentoDTO> predecessores = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.distancia));

        for (SegmentoDTO segmento : segmentos) {
            distanciaMinima.put(segmento.getPonto_inicial(), Double.POSITIVE_INFINITY);
            distanciaMinima.put(segmento.getPonto_final(), Double.POSITIVE_INFINITY);
        }

        distanciaMinima.put(pontoInicial, 0.0);
        priorityQueue.add(new Node(pontoInicial, null, 0.0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();

            if (distanciaMinima.get(currentNode.ponto) < currentNode.distancia) {
                continue;
            }

            for (SegmentoDTO segmento : segmentos) {
                if (segmento.getStatus() == 0) {
                    continue; // Skip blocked segments
                }

                if (segmento.getPonto_inicial().equals(currentNode.ponto)) {
                    double novaDistancia = currentNode.distancia + segmento.getDistancia();

                    if (novaDistancia < distanciaMinima.get(segmento.getPonto_final())) {
                        distanciaMinima.put(segmento.getPonto_final(), novaDistancia);
                        predecessores.put(segmento.getPonto_final(), segmento);

                        priorityQueue.add(new Node(segmento.getPonto_final(), currentNode, novaDistancia));
                    }
                }
            }
        }

        reconstruirMelhorRota(pontoFinal, predecessores);

        return melhorRota;
    }

    private void reconstruirMelhorRota(String pontoFinal, Map<String, SegmentoDTO> predecessores) {
        melhorRota.clear();
        SegmentoDTO segmentoAtual = predecessores.get(pontoFinal);

        while (segmentoAtual != null) {
            melhorRota.add(segmentoAtual);
            segmentoAtual = predecessores.get(segmentoAtual.getPonto_inicial());
        }

        Collections.reverse(melhorRota);
    }

   /* public List<SegmentoDTO> encontrarMelhorRota(String pontoInicial, String pontoFinal, List<SegmentoDTO> segmentos) {
        List<SegmentoDTO> rotaAtual = new ArrayList<>();
        boolean[] visitado = new boolean[segmentos.size()];

        encontrarMelhorRotaAux(pontoInicial, pontoFinal, segmentos, visitado, rotaAtual, 0);

        return melhorRota;
    }

    private void encontrarMelhorRotaAux(String pontoAtual, String pontoFinal, List<SegmentoDTO> segmentos, boolean[] visitado, List<SegmentoDTO> rotaAtual, double distanciaAtual) {
        if (pontoAtual.equals(pontoFinal)) {
            if (distanciaAtual < menorDistancia) {
                menorDistancia = distanciaAtual;
                melhorRota = new ArrayList<>(rotaAtual);
            }
            return;
        }

        for (int i = 0; i < segmentos.size(); i++) {
            SegmentoDTO segmento = segmentos.get(i);

            if (!visitado[i] && segmento.getPonto_inicial().equals(pontoAtual) && segmento.getStatus() != 1) {
                visitado[i] = true;
                rotaAtual.add(segmento);

                encontrarMelhorRotaAux(segmento.getPonto_final(), pontoFinal, segmentos, visitado, rotaAtual, distanciaAtual + segmento.getDistancia());

                visitado[i] = false;
                rotaAtual.remove(rotaAtual.size() - 1);
            }
        }
    }*/



}
