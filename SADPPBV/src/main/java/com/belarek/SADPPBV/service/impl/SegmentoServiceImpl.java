package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.segmentos.PostSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.PutSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;
import com.belarek.SADPPBV.entity.Ponto;
import com.belarek.SADPPBV.entity.Segmento;
import com.belarek.SADPPBV.repository.PontoRepository;
import com.belarek.SADPPBV.repository.SegmentoRepository;
import com.belarek.SADPPBV.service.SegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SegmentoServiceImpl implements SegmentoService {
    private SegmentoRepository segmentoRepository;
    private PontoRepository pontoRepository;

    @Autowired
    public SegmentoServiceImpl(SegmentoRepository segmentoRepository, PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
        this.segmentoRepository = segmentoRepository;
    }

    @Override
    public String createSegmento(PostSegmentoDTO segmento) {
        try {
            Segmento novoSegmento = mapToEntity(segmento);
            segmentoRepository.save(novoSegmento);
            return "success";
        } catch (Exception e) {
            return "erro";
        }
    }

    @Override
    public SegmentoDTO findById(Long id) {
        try {
            Segmento segmentoEncontrado = segmentoRepository.findById(id).orElse(null);
            if (segmentoEncontrado != null) {
                return mapToDTO(segmentoEncontrado);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String updateSegmento(PutSegmentoDTO segmento, Long id) {
        try {
            Segmento segmentoEncontrado = segmentoRepository.findById(id).get();

            if (segmentoEncontrado != null) {
                Ponto pontoInicial = pontoRepository.findById(Integer.valueOf(segmento.getPonto_inicial())).get();
                Ponto pontoFinal = pontoRepository.findById(Integer.valueOf(segmento.getPonto_final())).get();;
                segmentoEncontrado.setDistancia(segmento.getDistancia());
                segmentoEncontrado.setStatus(segmento.getStatus());
                segmentoEncontrado.setPonto_inicial(pontoInicial);
                segmentoEncontrado.setPonto_final(pontoFinal);
                segmentoEncontrado.setDirecao(segmento.getDirecao());
                segmentoRepository.save(segmentoEncontrado);
                return "success";
            } else {
                return "erro";
            }
        } catch (Exception e) {
            return "erro";
        }
    }

    @Override
    public String deleteSegmento(Long id) {
        try {
            Segmento segmentoEncontrado = segmentoRepository.findById(id).get();
            if(segmentoEncontrado!=null) {
                segmentoRepository.deleteById(id);
                return "success";
            }
            return "erro";
        } catch (Exception e) {
            return "erro";
        }
    }

    @Override
    public List<SegmentoDTO> listSegmentos() {
        try {
            List<Segmento> segmentoDTOList = segmentoRepository.findAll();
            return segmentoDTOList.stream().map(this::mapToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private SegmentoDTO mapToDTO(Segmento segmento){
        SegmentoDTO segmentoDTO = new SegmentoDTO();
        segmentoDTO.setDistancia(segmento.getDistancia());
        segmentoDTO.setPonto_inicial(segmento.getPonto_inicial().getNome());
        segmentoDTO.setPonto_final(segmento.getPonto_final().getNome());
        segmentoDTO.setStatus(segmento.getStatus());
        segmentoDTO.setDirecao(segmento.getDirecao());
        segmentoDTO.setSegmento_id(segmento.getSegmento_id());
        return segmentoDTO;
    }

    private Segmento mapToEntity(PostSegmentoDTO segmentoDTO){
        Segmento segmento = new Segmento();
        Ponto pontoInicial = pontoRepository.findById(Integer.valueOf(segmentoDTO.getPonto_inicial())).get();
        Ponto pontoFinal = pontoRepository.findById(Integer.valueOf(segmentoDTO.getPonto_final())).get();;
        segmento.setPonto_inicial(pontoInicial);
        segmento.setPonto_final(pontoFinal);
        segmento.setStatus(segmentoDTO.getStatus());
        segmento.setDirecao(segmentoDTO.getDirecao());
        segmento.setDistancia(segmentoDTO.getDistancia());
        segmento.setSegmento_id(segmentoDTO.getSegmento_id());
        return segmento;
    }
}
