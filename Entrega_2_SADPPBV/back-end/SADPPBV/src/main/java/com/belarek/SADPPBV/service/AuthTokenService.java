package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.TokenDTO;
import com.belarek.SADPPBV.dto.UserDTO;
import com.belarek.SADPPBV.entity.AuthToken;

public interface AuthTokenService {
    AuthToken findByToken(String token);
    void createToken(AuthToken token);
    void changeIsValid(AuthToken token);
    boolean verificarAuthTokenAtivo(String token);
}
