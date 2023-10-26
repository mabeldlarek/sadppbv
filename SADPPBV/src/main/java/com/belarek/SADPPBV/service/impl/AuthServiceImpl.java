package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.*;
import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.entity.User;
import com.belarek.SADPPBV.repository.TokenRepository;
import com.belarek.SADPPBV.repository.UserRepository;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.AuthService;
import com.belarek.SADPPBV.service.AuthTokenService;
import com.belarek.SADPPBV.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO login) {
        authenticationManager = context.getBean(AuthenticationManager.class);
      //  UserDetails u = userRepository.findByRegistro(login.getRegistro());
        User user = (User) userRepository.findByRegistro(login.getRegistro());
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(),login.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        AuthTokenDTO authTokenDTO = new AuthTokenDTO(token,user.getId(),true);
        authTokenService.createToken(mapToEntity(authTokenDTO));

        return new LoginResponseDTO(token, new ResponseDTO("Login realizado com sucesso", true));
    }

    @Override
    public String logout(String token) {
        AuthToken authToken = authTokenService.findByToken(token);
        authToken.setValido(false);
        authTokenService.changeIsValid(authToken);
        return "sucesso";
    }

    private AuthToken mapToEntity(AuthTokenDTO authTokenDTO){
        AuthToken authToken = new AuthToken();
        authToken.setToken(authTokenDTO.getToken());
        authToken.setUserId(authTokenDTO.getUserId());
        authToken.setValido(true);
        return authToken;
    }
}
