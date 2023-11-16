package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.*;
import com.belarek.SADPPBV.entity.User;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.UserService;
import com.belarek.SADPPBV.util.RegistrarLogsRequestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@CrossOrigin(origins = "*")
public class UserController {
    private UserService personService;
    private TokenService tokenService;
    @Autowired
    private RegistrarLogsRequestResponse log;
    @Autowired
    public UserController(UserService personService, TokenService tokenService) {
        this.personService = personService;
        this.response = new ResponseDTO();
        this.tokenService = tokenService;
    }

    private ResponseDTO response;

    @GetMapping("usuarios")
    public ResponseEntity<Object> listPersons(HttpServletRequest request) {
        log.addLogHeadBody(request,null);
        ResponseDTO responseDTO = null;
                List<GetUserDTO> usuarios = null;
        try {
            usuarios = personService.listPersons();
            if(usuarios != null) {
                response.setMessage("Lista de usuários encontrada com sucesso");
                response.setSucess(true);
                System.out.println("ENVIADO: " + response);
                log.addLogResponse(ResponseEntity.ok().body(new ListaUsuariosResponseDTO(usuarios, response)));
                return ResponseEntity.ok().body(new ListaUsuariosResponseDTO(usuarios, response));
            }
        }catch (Exception e){
            responseDTO = new ResponseDTO("Erro ao encontrar lista de usuarios", false);
            log.addLogResponse(ResponseEntity.status(403).body(responseDTO));
            return ResponseEntity.status(403).body(responseDTO);
        }
        responseDTO = new ResponseDTO("Usuário não encontrado", false);

        log.addLogResponse(ResponseEntity.status(403).body(responseDTO));

        return ResponseEntity.status(403).body(responseDTO);
    }

    @GetMapping("usuarios/{registro}")
    public ResponseEntity<Object> listPersonByRegistro(HttpServletRequest request, @PathVariable int registro) {
        GetUserDTO userDTO = null;
        String token = null;
        log.addLogHeadBody(request,registro);
        try {
            token = tokenService.recoverToken(request);
            if(token !=null) {
                if (personService.verificarAutorizacaoObterUsuario(token, registro)) {
                    userDTO = personService.findByRegister(registro);
                    if (userDTO != null) {
                        response.setMessage("Usuário encontrado com sucesso");
                        response.setSucess(true);
                        log.addLogResponse( ResponseEntity.ok().body(new UsuarioResponseDTO(userDTO, response)));
                        return ResponseEntity.ok().body(new UsuarioResponseDTO(userDTO, response));
                    } else {
                        response.setMessage("Erro ao encontrar o usuário");
                        response.setSucess(false);
                    }
                }
                else {
                    response.setMessage("Não autorizado: Você pode ver somente seu registro");
                    response.setSucess(false);
                }
            }
        } catch (Exception e){
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Erro ao encontrar usuario com registro :" + registro, false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao encontrar usuario com registro :" + registro, false));
        }

        log.addLogResponse(ResponseEntity.status(403).body(response));
        return ResponseEntity.status(403).body(response);
    }

    @PostMapping("usuarios")
    public ResponseEntity<ResponseDTO> createPerson(HttpServletRequest request, @RequestBody @Valid UserDTO personDTO) {
        log.addLogHeadBody(request,personDTO);
            try {
               String resultado = personService.createPerson(personDTO);
               if (resultado.equals("sucesso")) {
                   response.setMessage("Usuário criado com sucesso");
                   response.setSucess(true);
               } else {
                   response.setMessage(resultado);
                   response.setSucess(false);
               }
           } catch (Exception e){
                log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Erro ao realizar cadastro:" + e.getMessage(), false)));
                return ResponseEntity.status(403).body(new ResponseDTO("Erro ao realizar cadastro:" + e.getMessage(), false));
           }

            if(response.isSucess()){
                log.addLogResponse(ResponseEntity.ok(response));
                return ResponseEntity.ok(response);
            }

        log.addLogResponse(ResponseEntity.status(401).body(response));
        return ResponseEntity.status(401).body(response);
    }

    @PutMapping("usuarios/{registro}")
    public ResponseEntity<ResponseDTO> updatePerson(HttpServletRequest request,
                                                    @PathVariable int registro, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        String conteudoReq = "Corpo: " + userUpdateDTO.toString() + "Caminho: "+ registro;
        log.addLogHeadBody(request,conteudoReq);

        String token = null;
        token = tokenService.recoverToken(request);
        try{
            if(token !=null) {
                if (personService.verificarAutorizacaoObterUsuario(token, registro)) {
                    String resultado = personService.updatePerson(userUpdateDTO, registro);
                    if (resultado.equals("sucesso")) {
                        response.setMessage("Alteração no cadastrado realizada com sucesso.");
                        response.setSucess(true);
                        log.addLogResponse(ResponseEntity.ok().body(response));
                        return ResponseEntity.ok().body(response);
                    } else {
                        response.setMessage("Falha ao realizar alteração :" + resultado);
                        response.setSucess(false);
                    }
                }
                else {
                    response.setMessage("Não autorizado: Você pode editar somente o seu registro");
                    response.setSucess(false);
                }
            }
        } catch (Exception e){
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Erro ao realizar update: " + e.getMessage(), false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao realizar update: " + e.getMessage(), false));
        }

        log.addLogResponse(ResponseEntity.status(403).body(response));
        return ResponseEntity.status(403).body(response);
    }

    @DeleteMapping("usuarios/{registro}")
    public ResponseEntity<ResponseDTO> deletePerson(HttpServletRequest request, @PathVariable int registro) {
        log.addLogHeadBody(request,registro);
        try {
            if (personService.deleteByRegistro(registro)) {
                response.setMessage("O usuário foi apagado com sucesso.");
                response.setSucess(true);
            } else {
                response.setMessage("Usuário inexistente.");
                response.setSucess(false);
            }
        } catch (Exception e) {
            log.addLogResponse(ResponseEntity.status(403).body(new ResponseDTO("Erro ao deletar o usuário :" + e.getMessage(), false)));
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao deletar o usuário :" + e.getMessage(), false));
        }
        if(response.isSucess()){
            log.addLogResponse(ResponseEntity.ok().body(response));
            return ResponseEntity.ok().body(response);
        }
        log.addLogResponse(ResponseEntity.status(401).body(response));
        return ResponseEntity.status(401).body(response);
    }

}
