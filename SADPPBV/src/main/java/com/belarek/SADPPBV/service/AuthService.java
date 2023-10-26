package com.belarek.SADPPBV.service;

import com.belarek.SADPPBV.dto.LoginRequestDTO;
import com.belarek.SADPPBV.dto.LoginResponseDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO login);
    String logout(String token);
}
