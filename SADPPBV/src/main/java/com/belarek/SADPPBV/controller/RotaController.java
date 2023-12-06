package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.dto.RotaCalculadaDTO;
import com.belarek.SADPPBV.dto.RotaDTO;
import com.belarek.SADPPBV.dto.segmentos.ListSegmentoDTO;
import com.belarek.SADPPBV.dto.segmentos.SegmentoDTO;
import com.belarek.SADPPBV.service.RotaService;
import com.belarek.SADPPBV.service.SegmentoService;
import com.belarek.SADPPBV.util.RegistrarLogsRequestResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RestController
public class RotaController {
    private RotaService rotaService;
    private ResponseDTO response;
    @Autowired
    private RegistrarLogsRequestResponse log;

    @Autowired
    public RotaController(RotaService rotaService) {
        this.rotaService = rotaService;
        this.response = new ResponseDTO();
    }

    @PostMapping("rotas")
    public ResponseEntity<Object> postSegmentos(HttpServletRequest request, @RequestBody RotaDTO rota) {
        log.addLogHeadBody(request, rota);
        try {
            RotaCalculadaDTO resposta = rotaService.getRota(rota);
            if (resposta != null) {
                log.addLogResponse(ResponseEntity.ok().body(resposta) );
                return ResponseEntity.ok().body(resposta);
            } else{
                log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Nenhuma rota encontrada", false)));
                return ResponseEntity.status(403).body(new ResponseDTO("Nenhuma rota encontrada", false));
            }

        } catch (Exception e){
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Erro ao encontrar rota", false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao encontrar rota", false));
        }
    }
}
