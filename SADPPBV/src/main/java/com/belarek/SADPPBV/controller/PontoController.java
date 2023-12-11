package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.pontos.ListPontoDTO;
import com.belarek.SADPPBV.dto.pontos.PontoDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.dto.pontos.PontoPostPutDTO;
import com.belarek.SADPPBV.dto.pontos.GetPontoDTO;
import com.belarek.SADPPBV.service.PontoService;
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
public class PontoController {
    private PontoService pontoService;
    private ResponseDTO response;
    @Autowired
    private RegistrarLogsRequestResponse log;

    @Autowired
    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
        this.response = new ResponseDTO();
    }

    @GetMapping("pontos/{id}")
    public ResponseEntity<Object> listPontoById(@PathVariable Integer id) {
        PontoDTO resultado = pontoService.findById(id);
        if(resultado!=null){
            log.addLogResponse(ResponseEntity.ok().body(new GetPontoDTO(resultado, new ResponseDTO("Ponto encontrado com sucesso", true))));
            return ResponseEntity.ok().body(new GetPontoDTO(resultado, new ResponseDTO("Ponto encontrado com sucesso", true)));
        } else {
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Falha ao encontrar ponto", false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Falha ao encontrar ponto", false));
        }
    }

    @PutMapping("pontos/{id}")
    public ResponseEntity<ResponseDTO> updatePonto(@PathVariable Integer id, @RequestBody PontoPostPutDTO pontoDTO) {
        String resultado = pontoService.updatePonto(pontoDTO, id);
        if(resultado.equals("success")) {
            response.setMessage("Ponto atualizado com sucesso.");
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

    @DeleteMapping("pontos/{id}")
    public ResponseEntity<ResponseDTO> deletePonto(@PathVariable Integer id) {
        String resultado = pontoService.deletePonto(id);
        if(resultado.equals("success")) {
            response.setMessage("Ponto removido com sucesso.");
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

    @GetMapping("pontos")
    public ResponseEntity<Object> listpontos() {
        List<PontoDTO> pontos = pontoService.listPontos();
        if(!pontos.isEmpty()) {
            log.addLogResponse(ResponseEntity.ok().body(new ListPontoDTO(pontos,new ResponseDTO("Pontos encontrados", true))));
            return ResponseEntity.ok().body(new ListPontoDTO(pontos,new ResponseDTO("Pontos encontrados", true)));
        } else if (pontos.isEmpty()) {
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Não há pontos cadastrados", false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Não há pontos cadastrados", false));
        } else

            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter pontos", false)));
        return ResponseEntity.status(403).body(new ResponseDTO("Falha ao obter pontos", false));
    }

    @PostMapping("pontos")
    public ResponseEntity<ResponseDTO> createPonto(@RequestBody @Valid PontoPostPutDTO ponto) {
        String resultado = pontoService.createPonto(ponto);
        if(resultado.equals("success")){
            response.setMessage("Ponto criado com sucesso");
            response.setSuccess(true);
            log.addLogResponse(ResponseEntity.ok().body(response));
            return ResponseEntity.ok().body(response);
        }else {
            response.setMessage("Erro: " + resultado);
            response.setSuccess(false);
            log.addLogResponse(ResponseEntity.status(403).body(response));
            return ResponseEntity.status(403).body(response);
        }
    }
}
