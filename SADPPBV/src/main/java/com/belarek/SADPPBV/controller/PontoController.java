package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.PontoDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.service.PontoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class PontoController {
    private PontoService pontoService;
    private ResponseDTO response;

    @Autowired
    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
        this.response = new ResponseDTO();
    }

    @GetMapping("pontos")
    public ResponseEntity<List<PontoDTO>> listpontos() {
        return ResponseEntity.ok().body(pontoService.listPontos());
    }

    @PostMapping("pontos")
    public ResponseEntity<ResponseDTO> createPonto(@RequestBody @Valid PontoDTO pontoDTO) {
        pontoService.createPonto(pontoDTO);
        response.setMessage("Ponto criado com sucesso");
        response.setSucess(true);
        return ResponseEntity.ok().body(response);
    }
}
