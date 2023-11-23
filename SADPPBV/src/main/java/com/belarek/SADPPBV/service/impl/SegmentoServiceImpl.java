package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.SegmentoDTO;
import com.belarek.SADPPBV.entity.Segmento;
import com.belarek.SADPPBV.repository.SegmentoRepository;
import com.belarek.SADPPBV.service.SegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SegmentoServiceImpl implements SegmentoService {
    private SegmentoRepository segmentoRepository;

    @Autowired
    public SegmentoServiceImpl(SegmentoRepository segmentoRepository) {
        this.segmentoRepository = segmentoRepository;
    }

    @Override
    public String createSegmento(SegmentoDTO segmento) {
        Segmento novoSegmento = mapToEntity(segmento);
        segmentoRepository.save(novoSegmento);
        return "sucesso";
    }

    @Override
    public SegmentoDTO findById(Long id) {
        Segmento segmentoEncontrado = segmentoRepository.findById(id).get();
        return mapToDTO(segmentoEncontrado);
    }

    @Override
    public String updateSegmento(SegmentoDTO segmento, Long id) {
        try {
            Segmento segmentoEncontrado = segmentoRepository.findById(id).get();
            if(segmentoEncontrado!=null){
                segmentoEncontrado.setDirecao(segmento.getDirecao());
                segmentoEncontrado.setStatus(segmento.getStatus());
                segmentoEncontrado.setPonto_final(segmento.getPonto_final());
                segmentoEncontrado.setPonto_inicial(segmento.getPonto_inicial());
                segmentoEncontrado.setDistancia(segmento.getDistancia());
                segmentoRepository.save(segmentoEncontrado);
            }
        } catch (Exception e){
            return "Erro:" + e;
        }
        return "sucesso";
    }

    @Override
    public String deleteSegmento(Long id) {
        segmentoRepository.deleteById(id);
        return "sucesso";
    }

    @Override
    public List<SegmentoDTO> listSegmentos() {
        List<Segmento> segmentoDTOList = segmentoRepository.findAll();
        return segmentoDTOList.stream().map(segmento -> mapToDTO(segmento)).collect(Collectors.toList());
    }

    private SegmentoDTO mapToDTO(Segmento segmento){
        SegmentoDTO segmentoDTO = new SegmentoDTO();
        segmentoDTO.setPonto_inicial(segmento.getPonto_inicial());
        segmentoDTO.setPonto_final(segmento.getPonto_final());
        segmentoDTO.setStatus(segmento.getStatus());
        segmentoDTO.setDirecao(segmento.getDirecao());
        segmentoDTO.setSegmento_id(segmento.getSegmento_id());
        return segmentoDTO;
    }

    private Segmento mapToEntity(SegmentoDTO segmentoDTO){
        Segmento segmento = new Segmento();
        segmento.setPonto_inicial(segmento.getPonto_inicial());
        segmento.setPonto_final(segmento.getPonto_final());
        segmento.setStatus(segmento.getStatus());
        segmento.setDirecao(segmento.getDirecao());
        segmento.setDistancia(segmento.getDistancia());
        segmento.setSegmento_id(segmento.getSegmento_id());
        return segmento;
    }
}
