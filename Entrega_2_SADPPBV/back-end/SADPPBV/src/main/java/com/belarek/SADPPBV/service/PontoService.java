package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.PontoDTO;

import java.util.List;


public interface PontoService {
    String createPonto(PontoDTO Ponto);
    List<PontoDTO> listPontos();
}
