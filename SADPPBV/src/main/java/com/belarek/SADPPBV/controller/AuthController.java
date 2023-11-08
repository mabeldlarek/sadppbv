package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.LoginRequestDTO;
import com.belarek.SADPPBV.dto.LoginResponseDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.AuthService;
import com.belarek.SADPPBV.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
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
    public ResponseEntity<Object> login( @RequestBody @Valid LoginRequestDTO loginRequestDTO){
        try {
            authenticationManager = context.getBean(AuthenticationManager.class);
            Object respostaLogin = authorizationService.login(loginRequestDTO);
            if (respostaLogin instanceof ResponseDTO)
                return ResponseEntity.status(401).body(respostaLogin);
            else
                return ResponseEntity.ok().body(respostaLogin);
        } catch (ConstraintViolationException e){
            return ResponseEntity.badRequest().body(new ResponseDTO("Erro:" + e, false));
        }
    }

   @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){
        String token = extractTokenFromRequest(request);
        ResponseDTO response = null;
        if (token != null) {
            response = authorizationService.logout(token);
        }

        if(response!= null && response.isSucess())
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(401).body(response);
   }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
