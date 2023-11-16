package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.GetUserDTO;
import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(String email);
    GetUserDTO findByRegister(int registro);
    String createPerson(UserDTO person);
    String updatePerson(UserUpdateDTO person, int registro);
    boolean deleteByRegistro(int registro);
    List<GetUserDTO> listPersons();
    boolean verificarAutorizacaoObterUsuario(String token, int registroSolicitado);

}
