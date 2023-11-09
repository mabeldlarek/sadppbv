package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.dto.*;
import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.entity.User;
import com.belarek.SADPPBV.repository.UserRepository;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.AuthService;
import com.belarek.SADPPBV.service.AuthTokenService;
import com.belarek.SADPPBV.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private UserRepository userRepository;
    @Autowired
    private AuthTokenService authTokenService;

    @Override
    public Object login(LoginRequestDTO login) {
        Object respostaLogin = null;
        authenticationManager = context.getBean(AuthenticationManager.class);
        User user = (User) userRepository.findByRegistro(login.getRegistro());

        if(user!=null) {
            String token = autenticarUsuario(user, login.getSenha());
            if (token.isEmpty())
                respostaLogin = getRespostaUsuarioNaoCredenciado();
            else
                respostaLogin = new LoginResponseDTO(login.getRegistro(), token, "Login realizado com sucesso", true);
        }

        return  respostaLogin;
    }

    @Override
    public ResponseDTO logout(String token) {
        AuthToken authToken = authTokenService.findByToken(token);
        if(authToken!= null){
            authToken.setValido(false);
            authTokenService.changeIsValid(authToken);
            return new ResponseDTO("Logout realizado com sucesso", true);
        }

        return new ResponseDTO("Não autenticado", false);
    }

    private AuthToken mapToEntity(AuthTokenDTO authTokenDTO){
        AuthToken authToken = new AuthToken();
        authToken.setToken(authTokenDTO.getToken());
        authToken.setUserId(authTokenDTO.getUserId());
        authToken.setValido(true);
        return authToken;
    }

    private String autenticarUsuario(User user, String senha){
        String token = "";
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(), senha, user.getAuthorities());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            token = tokenService.generateToken((User) auth.getPrincipal());
            criarToken(token, user.getId());
        } catch (AuthenticationException e){
            return token;
        }
        return token;
    }

    private void criarToken(String token, Long idUser){
        AuthTokenDTO authTokenDTO = new AuthTokenDTO(token,idUser,true);
        authTokenService.createToken(mapToEntity(authTokenDTO));
    }
    
    private ResponseDTO getRespostaUsuarioNaoCredenciado(){
        return new ResponseDTO("Credenciais Inválidas", false);
    }

}
