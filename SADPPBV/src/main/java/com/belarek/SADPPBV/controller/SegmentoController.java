package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.dto.segmentos.GetSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.ListSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.PutSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;
import com.belarek.SADPPBV.dto.usuarios.ListaUsuariosResponseDTO;
import com.belarek.SADPPBV.service.SegmentoService;
import com.belarek.SADPPBV.util.RegistrarLogsRequestResponse;
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
    public ResponseEntity<Object> listSegmentos() {
        List<SegmentoDTO> segmentos = segmentoService.listSegmentos();
        if(!segmentos.isEmpty()){
            log.addLogResponse(ResponseEntity.ok().body(new ListSegmentoDTO(segmentos,new ResponseDTO("Segmentos encontrados", true))));

            return ResponseEntity.ok().body(new ListSegmentoDTO(segmentos,new ResponseDTO("Segmentos encontrados", true)));
        }
        log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmentos", false)));
        return ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmentos", false));
    }
    @GetMapping("segmentos/{id}")
    public ResponseEntity<Object> listSegmentoById(@PathVariable Long id) {
        SegmentoDTO resultado = segmentoService.findById(id);
        if(resultado!=null){
            return ResponseEntity.ok().body(new GetSegmentoDTO(resultado, new ResponseDTO("Segmento encontrado com sucesso", true)));
        } else
            return ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter segmento", false));
    }

    @PostMapping("segmentos")
    public ResponseEntity<ResponseDTO> createSegmento(@RequestBody @Valid SegmentoDTO SegmentoDTO) {
        String resultado = segmentoService.createSegmento(SegmentoDTO);
        if(resultado.equals("success")){
            response.setMessage("Segmento criado com sucesso");
            response.setSuccess(true);
            return ResponseEntity.ok().body(response);
        } else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            return ResponseEntity.status(403).body(response);
        }
    }

    @PutMapping("segmentos/{id}")
    public ResponseEntity<ResponseDTO> updateSegmento(@PathVariable Long id, @RequestBody PutSegmentoDTO putSegmentoDTO) {
        String resultado = segmentoService.updateSegmento(putSegmentoDTO, id);
        if(resultado.equals("success")) {
            response.setMessage("Segmento atualizado com sucesso.");
            response.setSuccess(true);
            return ResponseEntity.ok().body(response);
        } else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            return ResponseEntity.status(403).body(response);
        }
    }

    @DeleteMapping("segmentos/{id}")
    public ResponseEntity<ResponseDTO> deleteSegmento(@PathVariable Long id) {
        String resultado = segmentoService.deleteSegmento(id);
        if(resultado.equals("success")) {
            response.setMessage("Segmento removido com sucesso.");
            response.setSuccess(true);
            return ResponseEntity.ok().body(response);
        } else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            return ResponseEntity.status(403).body(response);
        }
    }
}
