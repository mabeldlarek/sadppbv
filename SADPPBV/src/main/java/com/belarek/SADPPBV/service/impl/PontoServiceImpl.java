package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.pontos.PontoDTO;
import com.belarek.SADPPBV.dto.pontos.PontoDTO;
import com.belarek.SADPPBV.dto.pontos.PontoPostPutDTO;
import com.belarek.SADPPBV.entity.Ponto;
import com.belarek.SADPPBV.entity.Ponto;
import com.belarek.SADPPBV.entity.Ponto;
import com.belarek.SADPPBV.entity.Ponto;
import com.belarek.SADPPBV.repository.PontoRepository;
import com.belarek.SADPPBV.service.PontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PontoServiceImpl implements PontoService {
    
    @Autowired
    private PontoRepository pontoRepository;

    @Override
    public String createPonto(PontoPostPutDTO ponto) {
        try{
            pontoRepository.save(mapToEntity(ponto));
            return "success";
        } catch (Exception e) {
            return "erro";
        }
    }

    @Override
    public List<PontoDTO> listPontos() {
        try{
            List<Ponto> pontoDTOList = pontoRepository.findAll();
            return pontoDTOList.stream().map(this::mapToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public PontoDTO findById(Long id) {
        try {
            Ponto pontoEncontrado = pontoRepository.findById(id).orElse(null);
            if (pontoEncontrado != null) {
                return mapToDTO(pontoEncontrado);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String updatePonto(PontoPostPutDTO ponto, Long id) {
        try {
            Ponto pontoEncontrado = pontoRepository.findById(id).get();
            if (pontoEncontrado != null) {
                pontoEncontrado.setNome(ponto.getNome());
                pontoRepository.save(pontoEncontrado);
                return "success";
            } else {
                return "erro";
            }
        } catch (Exception e) {
            return "erro";
        }
    }

    @Override
    public String deletePonto(Long id) {
        try {
            Ponto pontoEncontrado = pontoRepository.findById(id).get();
            if(pontoEncontrado!=null) {
                pontoRepository.deleteById(id);
                return "success";
            }
            return "erro";
        } catch (Exception e) {
            return "erro";
        }
    }

    private Ponto mapToEntity(PontoPostPutDTO pontoPostPut){
        Ponto ponto = new Ponto();
        ponto.setNome(ponto.getNome());
        return ponto;
    }

    private PontoDTO mapToDTO(Ponto ponto){
        PontoDTO pontoDTO = new PontoDTO();
        pontoDTO.setNome(ponto.getNome());
        ponto.setId(ponto.getId());
        return pontoDTO;
    }

    private Ponto mapToEntity(PontoDTO pontoDTO){
        Ponto ponto = new Ponto();
        ponto.setNome(pontoDTO.getNome());
        ponto.setId(ponto.getId());
        return ponto;
    }
}
