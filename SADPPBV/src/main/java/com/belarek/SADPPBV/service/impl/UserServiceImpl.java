package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.entity.User;
import com.belarek.SADPPBV.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.belarek.SADPPBV.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository personRepository;
    @Autowired
    public UserServiceImpl(UserRepository personRepository) {
        this.personRepository = personRepository;
    }

    public UserDTO findByEmail(String email){
        User person = (User) personRepository.findByEmail(email);
        return mapToDTO(person);
    }

    @Override
    public UserDTO findByRegister(int registro) {
        User person = (User) personRepository.findByRegistro(registro);
        return mapToDTO(person);
    }

    @Override
    public String createPerson(UserDTO personDTO) {
        User person = new User();
        String senhaEncriptografada = new BCryptPasswordEncoder().encode(personDTO.getSenha());
        person.setNome(personDTO.getNome());
        person.setEmail(personDTO.getEmail());
        person.setSenha(senhaEncriptografada);
        person.setRegistro(personDTO.getRegistro());
        person.setTipo_usuario(personDTO.getTipo_usuario());
        personRepository.save(person);
        return "sucesso";
    }

    @Override
    public UserDTO findById(Long id) {
        User person = personRepository.findById(id).get();
        UserDTO personDTO = new UserDTO();
        personDTO.setEmail(person.getEmail());
        personDTO.setNome(person.getNome());
        personDTO.setRegistro(person.getRegistro());
        personDTO.setSenha(person.getSenha());
        personDTO.setTipo_usuario(person.getTipo_usuario());
        return personDTO;
    }

    @Override
    public String updatePerson(UserDTO personDTO, Long id) {
        User person = personRepository.findById(id).get();
        person.setNome(personDTO.getNome());
        person.setEmail(personDTO.getEmail());
        person.setSenha(personDTO.getSenha());
        person.setRegistro(personDTO.getRegistro());
        person.setTipo_usuario(personDTO.getTipo_usuario());
        personRepository.save(person);
        return "sucesso";
    }

    @Override
    public String deletePerson(Long id) {
        User personDeleted = personRepository.findById(id).get();
        personRepository.delete(personDeleted);
        return null;
    }

    @Override
    public String deletePersonByRegistro(int registro) {
        personRepository.deleteByRegistro(registro);
        return null;
    }

    @Override
    public List<UserDTO> listPersons() {
        List<User> personDTOList = personRepository.findAll();
        return personDTOList.stream().map(person -> mapToDTO(person)).collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User person){
        UserDTO personDTO = new UserDTO();
        personDTO.setNome(person.getNome());
        personDTO.setEmail(person.getEmail());
        personDTO.setSenha(person.getSenha());
        personDTO.setRegistro(person.getRegistro());
        personDTO.setTipo_usuario(person.getTipo_usuario());
        return personDTO;
    }

    private User mapToEntity(UserDTO personDTO){
        User person = new User();
        person.setNome(personDTO.getNome());
        person.setEmail(personDTO.getEmail());
        person.setSenha(personDTO.getSenha());
        person.setRegistro(personDTO.getRegistro());
        person.setTipo_usuario(personDTO.getTipo_usuario());
        return person;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email);
    }
}
