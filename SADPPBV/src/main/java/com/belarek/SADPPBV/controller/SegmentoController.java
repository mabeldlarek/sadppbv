package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.dto.SegmentoDTO;
import com.belarek.SADPPBV.service.SegmentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SegmentoController {
    private SegmentoService segmentoService;
    private ResponseDTO response;
     
    @Autowired
    public SegmentoController(SegmentoService segmentoService) {
        this.segmentoService = segmentoService;
        this.response = new ResponseDTO();
    }

    @GetMapping("segmentos")
    public ResponseEntity<List<SegmentoDTO>> listSegmentos() {
        return ResponseEntity.ok().body(segmentoService.listSegmentos());
    }
    @GetMapping("segmentos/{id}")
    public ResponseEntity<SegmentoDTO> listSegmentoById(@PathVariable Long id) {
        return ResponseEntity.ok().body(segmentoService.findById(id));
    }

    @PostMapping("segmentos")
    public ResponseEntity<ResponseDTO> createSegmento(@RequestBody @Valid SegmentoDTO SegmentoDTO) {
        segmentoService.createSegmento(SegmentoDTO);
        response.setMessage("Segmento criado com sucesso");
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("segmento/{id}")
    public ResponseEntity<ResponseDTO> updateSegmento(@PathVariable Long id, @RequestBody SegmentoDTO SegmentoDTO) {
        segmentoService.updateSegmento(SegmentoDTO, id);
        response.setMessage("Segmento atualizado com sucesso.");
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("segmento/{id}")
    public ResponseEntity<ResponseDTO> deleteSegmento(@PathVariable Long id) {
        segmentoService.deleteSegmento(id);
        response.setMessage("Segmento removido com sucesso.");
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }
}
