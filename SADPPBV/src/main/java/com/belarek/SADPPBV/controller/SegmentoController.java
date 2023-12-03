package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.dto.segmentos.*;
import com.belarek.SADPPBV.service.SegmentoService;
import com.belarek.SADPPBV.util.RegistrarLogsRequestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RestController
public class SegmentoController {
    private SegmentoService segmentoService;
    private ResponseDTO response;
    private RegistrarLogsRequestResponse log;
     
    @Autowired
    public SegmentoController(SegmentoService segmentoService) {
        this.segmentoService = segmentoService;
        this.response = new ResponseDTO();
    }

    @GetMapping("segmentos")
    public ResponseEntity<Object> listSegmentos(HttpServletRequest request) {
        log.addLogHeadBody(request, null);
        List<SegmentoDTO> segmentos = segmentoService.listSegmentos();
        if(!segmentos.isEmpty()){
            log.addLogResponse(ResponseEntity.ok().body(new ListSegmentoDTO(segmentos,new ResponseDTO("Segmentos encontrados", true))));

            return ResponseEntity.ok().body(new ListSegmentoDTO(segmentos,new ResponseDTO("Segmentos encontrados", true)));
        }
        log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmentos", false)));
        return ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmentos", false));
    }
    @GetMapping("segmentos/{id}")
    public ResponseEntity<Object> listSegmentoById(HttpServletRequest request, @PathVariable Long id) {
        log.addLogHeadBody(request, id);
        SegmentoDTO resultado = segmentoService.findById(id);
        if(resultado!=null){
            log.addLogResponse(ResponseEntity.ok().body(new GetSegmentoDTO(resultado, new ResponseDTO("Segmento encontrado com sucesso", true))));
            return ResponseEntity.ok().body(new GetSegmentoDTO(resultado, new ResponseDTO("Segmento encontrado com sucesso", true)));
        } else {
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmento", false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmento", false));
        }
    }

    @PostMapping("segmentos")
    public ResponseEntity<ResponseDTO> createSegmento(HttpServletRequest request, @RequestBody @Valid PostSegmentoDTO postSegmentoDTO) {
        log.addLogHeadBody(request, postSegmentoDTO);
        String resultado = segmentoService.createSegmento(postSegmentoDTO);
        if(resultado.equals("success")){
            response.setMessage("Segmento criado com sucesso");
            response.setSuccess(true);
            log.addLogResponse(ResponseEntity.ok().body(response));
            return ResponseEntity.ok().body(response);
        } else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            log.addLogResponse(ResponseEntity.status(403).body(response));
            return ResponseEntity.status(403).body(response);
        }
    }

    @PutMapping("segmentos/{id}")
    public ResponseEntity<ResponseDTO> updateSegmento(HttpServletRequest request, @PathVariable Long id, @RequestBody PutSegmentoDTO putPostSegmentoDTO) {
        log.addLogHeadBody(request, putPostSegmentoDTO);
        String resultado = segmentoService.updateSegmento(putPostSegmentoDTO, id);
        if(resultado.equals("success")) {
            response.setMessage("Segmento atualizado com sucesso.");
            response.setSuccess(true);
            log.addLogResponse(ResponseEntity.ok().body(response));
            return ResponseEntity.ok().body(response);
        } else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            log.addLogResponse(ResponseEntity.status(403).body(response));
            return ResponseEntity.status(403).body(response);
        }
    }

    @DeleteMapping("segmentos/{id}")
    public ResponseEntity<ResponseDTO> deleteSegmento(HttpServletRequest request, @PathVariable Long id) {
        log.addLogHeadBody(request, id);
        String resultado = segmentoService.deleteSegmento(id);
        if(resultado.equals("success")) {
            response.setMessage("Segmento removido com sucesso.");
            response.setSuccess(true);
            log.addLogResponse(ResponseEntity.ok().body(response));
            return ResponseEntity.ok().body(response);
        } else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            log.addLogResponse(ResponseEntity.status(403).body(response));
            return ResponseEntity.status(403).body(response);
        }
    }
}
