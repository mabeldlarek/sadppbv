package com.belarek.SADPPBV.service.impl;

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
    private List<SegmentoDTO> melhorRota = null;
    private double menorDistancia = Double.MAX_VALUE;

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
    }


}
