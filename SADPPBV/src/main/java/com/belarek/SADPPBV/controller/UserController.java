package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.*;
import com.belarek.SADPPBV.entity.User;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Controller
public class UserController {
    private UserService personService;
    private TokenService tokenService;
    @Autowired
    public UserController(UserService personService, TokenService tokenService) {
        this.personService = personService;
        this.response = new ResponseDTO();
        this.tokenService = tokenService;
    }

    private ResponseDTO response;

    @GetMapping("usuarios")
    public ResponseEntity<Object> listPersons() {
        List<UserDTO> usuarios = null;
        try {
            usuarios = personService.listPersons();
            if(usuarios != null) {
                response.setMessage("Lista de usuários encontrada com sucesso");
                response.setSucess(true);
                return ResponseEntity.ok().body(new ListaUsuariosResponseDTO(usuarios, response));
            }
        }catch (Exception e){
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao encontrar lista de usuarios", false));
        }
        return ResponseEntity.status(403).body(new ResponseDTO("Usuário não encontrado", false));
    }

    @GetMapping("usuarios/{registro}")
    public ResponseEntity<Object> listPersonByRegistro(HttpServletRequest request, @PathVariable int registro) {
        UserDTO userDTO = null;
        String token = null;
        try {
            token = tokenService.recoverToken(request);
            if(token !=null) {
                if (personService.verificarAutorizacaoObterUsuario(token, registro)) {
                    userDTO = personService.findByRegister(registro);
                    if (userDTO != null) {
                        response.setMessage("Usuário encontrado com sucesso");
                        response.setSucess(true);
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
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao encontrar usuario com registro :" + registro, false));
        }
        return ResponseEntity.status(403).body(response);
    }

    @PostMapping("usuarios")
    public ResponseEntity<ResponseDTO> createPerson(@RequestBody @Valid UserDTO personDTO) {
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
               return ResponseEntity.status(403).body(new ResponseDTO("Erro ao realizar cadastro:" + e.getMessage(), false));
           }

        return ResponseEntity.ok(response);
    }

    @PutMapping("usuarios/{registro}")
    public ResponseEntity<ResponseDTO> updatePerson(HttpServletRequest request,
                                                    @PathVariable int registro, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        String token = null;
        token = tokenService.recoverToken(request);
        try{
            if(token !=null) {
                if (personService.verificarAutorizacaoObterUsuario(token, registro)) {
                    String resultado = personService.updatePerson(userUpdateDTO, registro);
                    if (resultado.equals("sucesso")) {
                        response.setMessage("Alteração no cadastrado realizada com sucesso.");
                        response.setSucess(true);
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
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao realizar update: " + e.getMessage(), false));
        }
        return ResponseEntity.status(403).body(response);
    }

    @DeleteMapping("usuarios/{registro}")
    public ResponseEntity<ResponseDTO> deletePerson(@PathVariable int registro) {
        try {
            if (personService.deleteByRegistro(registro)) {
                response.setMessage("O usuário foi apagado com sucesso.");
                response.setSucess(true);
            } else {
                response.setMessage("Usuário inexistente.");
                response.setSucess(false);
            }
        } catch (Exception e) {
            return ResponseEntity.status(403).body(new ResponseDTO("Erro ao deletar o usuário :" + e.getMessage(), false));
        }
        return ResponseEntity.ok().body(response);
    }

}
