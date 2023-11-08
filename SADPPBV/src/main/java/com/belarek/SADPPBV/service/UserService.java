package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(String email);
    UserDTO findByRegister(int registro);
    String createPerson(UserDTO person);
    UserDTO findById(Long id);
    String updatePerson(UserDTO person, Long id);
    String deletePerson(Long id);
    String deletePersonByRegistro(int registro);
    List<UserDTO> listPersons();
}
