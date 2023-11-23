package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.GetUserDTO;
import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.dto.UserUpdateDTO;
import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.entity.User;
import com.belarek.SADPPBV.repository.TokenRepository;
import com.belarek.SADPPBV.service.UserService;
import jakarta.transaction.Transactional;
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

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    @Autowired
    public UserServiceImpl(UserRepository personRepository, TokenRepository tokenRepository) {
        this.userRepository = personRepository;
        this.tokenRepository = tokenRepository;
    }

    public UserDTO findByEmail(String email){
        User person = (User) userRepository.findByEmail(email);
        if(person != null){
            return mapToDTO(person);
        }
        return null;
    }

    @Override
    public GetUserDTO findByRegister(int registro) {
        User person = (User) userRepository.findByRegistro(registro);
        if(person!=null) {
            return mapToGetDTO(person);
        }
        return null;
    }

    @Override
    public String createPerson(UserDTO personDTO) {
        GetUserDTO user = findByRegister(personDTO.getRegistro());
        if(user == null) {
            UserDTO u = findByEmail(personDTO.getEmail());
            if(u!=null){
                return "Email já existe";
            }
            User person = new User();
            String senhaEncriptografada = new BCryptPasswordEncoder().encode(personDTO.getSenha());
            person.setNome(personDTO.getNome());
            person.setEmail(personDTO.getEmail());
            person.setSenha(senhaEncriptografada);
            person.setRegistro(personDTO.getRegistro());
            person.setTipo_usuario(personDTO.getTipo_usuario());
            userRepository.save(person);
            return "success";
        } else
            return "Registro já existe.";
    }

    @Override
    public String updatePerson(UserUpdateDTO personDTO, int registro) {
        User user = userRepository.findByRegistro(registro);
        String senhaEncriptografada = "";
        if (user != null) {
            UserDetails uEmail = userRepository.findByEmail(personDTO.getEmail());
            if(!personDTO.getEmail().equals(user.getEmail()) && uEmail!=null)
                return "Email já existe";
            if(personDTO.getSenha().isEmpty() || personDTO.getSenha().equals("d41d8cd98f00b204e9800998ecf8427e")){
                senhaEncriptografada = user.getSenha();
            } else {
               senhaEncriptografada = new BCryptPasswordEncoder().encode(personDTO.getSenha());
            }
            user.setNome(personDTO.getNome());
            user.setEmail(personDTO.getEmail());
            user.setSenha(senhaEncriptografada);
            user.setRegistro(user.getRegistro());
            user.setTipo_usuario(user.getTipo_usuario());
            userRepository.save(user);

            return "success";
        }
        return "Usuário não encontrado";
    }

    @Transactional
    @Override
    public boolean deleteByRegistro(int registro) {
        GetUserDTO personDeleted = findByRegister(registro);
        if (personDeleted != null) {
            userRepository.deleteByRegistro(registro);
            return true;
        }
        return false;
    }


    @Override
    public List<GetUserDTO> listPersons() {
        List<User> personDTOList = userRepository.findAll();
        return personDTOList.stream().map(person -> mapToGetDTO(person)).collect(Collectors.toList());
    }

    @Override
    public boolean verificarAutorizacaoObterUsuario(String token, int registroSolicitado) {
        AuthToken authToken = tokenRepository.findByToken(token);
        if(authToken!=null) {
            User user = userRepository.findById(authToken.getUserId()).get();
            if (user.getAuthorities().stream().anyMatch(authority ->
                    "ROLE_ADMIN".equals(authority.getAuthority())))
                return true;

            if (user.getRegistro() == registroSolicitado) {
                return true;
            }
        }

        return false;
    }

    private UserDTO mapToDTO(User person){
        UserDTO personDTO = new UserDTO();
        personDTO.setNome(person.getNome());
        personDTO.setEmail(person.getEmail());
        personDTO.setRegistro(person.getRegistro());
        personDTO.setTipo_usuario(person.getTipo_usuario());
        return personDTO;
    }

    private GetUserDTO mapToGetDTO(User person){
        GetUserDTO personDTO = new GetUserDTO();
        personDTO.setNome(person.getNome());
        personDTO.setEmail(person.getEmail());
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
        return userRepository.findByEmail(email);
    }
}
