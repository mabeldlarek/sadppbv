package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.service.UserService;
import jakarta.validation.Valid;
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
    @Autowired
    public UserController(UserService personService) {

        this.personService = personService;
        this.response = new ResponseDTO();
    }

    private ResponseDTO response;

    @GetMapping("usuarios")
    public ResponseEntity<List<UserDTO>> listPersons() {
        return ResponseEntity.ok().body(personService.listPersons());
    }
    @GetMapping("usuarios/{id}")
    public ResponseEntity<UserDTO> listPersonById(@PathVariable Long id) {
        return ResponseEntity.ok().body(personService.findById(id));
    }

    @PostMapping("usuarios")
    public ResponseEntity<ResponseDTO> createPerson(@RequestBody @Valid UserDTO personDTO) {
        personService.createPerson(personDTO);
        response.setMessage("Usuário criado com sucesso");
        response.setSucess(true);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("usuarios/{id}")
    public ResponseEntity<ResponseDTO> updatePerson(@PathVariable Long id, @RequestBody UserDTO personDTO) {
        personService.updatePerson(personDTO, id);
        response.setMessage("Alteração no cadastrado realizada com sucesso.");
        response.setSucess(true);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("usuarios/{id}")
    public ResponseEntity<ResponseDTO> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        response.setMessage("O usuário foi apagado com sucesso.");
        response.setSucess(true);
        return ResponseEntity.ok().body(response);
    }
}
