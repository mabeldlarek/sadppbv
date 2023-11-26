package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.auth.LoginRequestDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;

public interface AuthService {
    Object login(LoginRequestDTO login);
    ResponseDTO logout(String token);
}
