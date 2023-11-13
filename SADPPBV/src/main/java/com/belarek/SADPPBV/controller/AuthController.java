package com.belarek.SADPPBV.controller;

import com.belarek.SADPPBV.dto.LoginRequestDTO;
import com.belarek.SADPPBV.dto.LoginResponseDTO;
import com.belarek.SADPPBV.dto.ResponseDTO;
import com.belarek.SADPPBV.security.TokenService;
import com.belarek.SADPPBV.service.AuthService;
import com.belarek.SADPPBV.service.UserService;
import com.belarek.SADPPBV.util.RegistrarLogsRequestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private RegistrarLogsRequestResponse log;
    private Set<String> tokenBlacklist = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(RegistrarLogsRequestResponse.class);

    @Autowired
    public AuthController(AuthService authorizationService, UserService userService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.loginResponseDTO = new LoginResponseDTO();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login( @RequestBody @Valid LoginRequestDTO loginRequestDTO, HttpServletRequest request){
        ResponseEntity response = null;

        try {
            authenticationManager = context.getBean(AuthenticationManager.class);
            Object respostaLogin = authorizationService.login(loginRequestDTO);
            if (respostaLogin instanceof ResponseDTO) {
                response = ResponseEntity.status(401).body(respostaLogin);
            }
            else {
                response = ResponseEntity.ok().body(respostaLogin);
            }
        } catch (ConstraintViolationException e){
            response = ResponseEntity.status(403).body(new ResponseDTO("Erro:" + e, false));
        }

        logger.info(response.toString());

        return response;
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
