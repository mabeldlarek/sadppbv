package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.segmentos.PostSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.PutSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;
import com.belarek.SADPPBV.entity.Segmento;

import java.util.List;

public interface SegmentoService {
    String createSegmento(PostSegmentoDTO postSegmento);
    SegmentoDTO findById(Long id);
    String updateSegmento(PutSegmentoDTO segmento, Long id);
    String deleteSegmento(Long id);
    List<SegmentoDTO> listSegmentos();
}
