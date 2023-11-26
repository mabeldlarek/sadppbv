package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;

import java.util.List;

public interface SegmentoService {
    String createSegmento(SegmentoDTO Segmento);
    SegmentoDTO findById(Long id);
    String updateSegmento(SegmentoDTO Segmento, Long id);
    String deleteSegmento(Long id);
    List<SegmentoDTO> listSegmentos();
}
