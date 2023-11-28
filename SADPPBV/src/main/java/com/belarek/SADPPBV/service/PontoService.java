package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.pontos.GetPontoDTO;
import com.belarek.SADPPBV.dto.pontos.PontoDTO;
import com.belarek.SADPPBV.dto.pontos.PontoPostPutDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;

import java.util.List;


public interface PontoService {
    String createPonto(PontoPostPutDTO Ponto);
    List<PontoDTO> listPontos();
    PontoDTO findById(Integer id);
    String updatePonto(PontoPostPutDTO Ponto, Integer id);
    String deletePonto(Integer id);
}
