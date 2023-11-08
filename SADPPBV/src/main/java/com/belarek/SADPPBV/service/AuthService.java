package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.LoginRequestDTO;
import com.belarek.SADPPBV.dto.LoginResponseDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;

public interface AuthService {
    Object login(LoginRequestDTO login);
    ResponseDTO logout(String token);
}
