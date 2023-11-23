package com.belarek.SADPPBV.service.impl;

import com.belarek.SADPPBV.entity.AuthToken;
import com.belarek.SADPPBV.repository.TokenRepository;
import com.belarek.SADPPBV.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenImpl implements AuthTokenService {

    @Autowired
    private TokenRepository tokenRepository;
    @Override
    public AuthToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void createToken(AuthToken token) {
        tokenRepository.save(token);
    }

    @Override
    public void changeIsValid(AuthToken token) {
        tokenRepository.save(token);
    }

    @Override
    public boolean verificarAuthTokenAtivo(String token) {
        AuthToken authToken = tokenRepository.findByToken(token);
        if(authToken != null && authToken.isValido()){
            return true;
        }
        return false;
    }
}
