package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(String email);
    UserDTO findByRegister(int registro);
    String createPerson(UserDTO person);
    UserDTO findById(Long id);
    String updatePerson(UserUpdateDTO person, int registro);
    boolean deleteByRegistro(int registro);
    List<UserDTO> listPersons();
    boolean verificarAutorizacaoObterUsuario(String token, int registroSolicitado);

}
