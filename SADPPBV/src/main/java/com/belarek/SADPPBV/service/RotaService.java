package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.RotaCalculadaDTO;
import com.belarek.SADPPBV.dto.RotaDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;

import java.util.List;

public interface RotaService {

    RotaCalculadaDTO getRota(RotaDTO rotaDTO);
    List<SegmentoDTO> calcularRota(String pontoInicial, String pontoFinal);
}
