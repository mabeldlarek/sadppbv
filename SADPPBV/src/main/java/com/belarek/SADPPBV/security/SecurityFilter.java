package com.belarek.SADPPBV.security;

import com.belarek.SADPPBV.repository.UserRepository;
import com.belarek.SADPPBV.service.AuthTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter{
    @Autowired 
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthTokenService authTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = tokenService.recoverToken(request);
    
        if (token != null) {
            if(authTokenService.verificarAuthTokenAtivo(token)) {
                var email = tokenService.validateToken(token);

                UserDetails user = userRepository.findByEmail(email);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
