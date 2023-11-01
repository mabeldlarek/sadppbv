package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.LoginRequestDTO;
import com.belarek.SADPPBV.dto.LoginResponseDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.AuthService;
import com.belarek.SADPPBV.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    private LoginResponseDTO loginResponseDTO;
    private AuthService authorizationService;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ApplicationContext context;
    private Set<String> tokenBlacklist = new HashSet<>();


    @Autowired
    public AuthController(AuthService authorizationService, UserService userService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.loginResponseDTO = new LoginResponseDTO();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        authenticationManager = context.getBean(AuthenticationManager.class);
        LoginResponseDTO respostaLogin = authorizationService.login(loginRequestDTO);
        if(respostaLogin.getToken().isEmpty())
            return ResponseEntity.ok().body(respostaLogin);

        return ResponseEntity.ok().body(respostaLogin);
    }

    @PostMapping("/logout2")
    public ResponseEntity<Object> eta(HttpServletRequest request){
        String token = extractTokenFromRequest(request);
        if (token != null) {
            authorizationService.logout(token);
        } else {
            return ResponseEntity.badRequest().body(new ResponseDTO("Nenhum token encontrado", false));
        }
        return ResponseEntity.ok().body(new ResponseDTO("Logout reaizado com sucesso", true));
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
