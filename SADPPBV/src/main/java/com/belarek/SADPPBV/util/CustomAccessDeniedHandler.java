package com.belarek.SADPPBV.util;

import com.belarek.SADPPBV.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import java.io.IOException;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private RegistrarLogsRequestResponse log;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        ResponseDTO responseDTO = new ResponseDTO("Não autorizado", false);
        String errorResponseJson = objectMapper.writeValueAsString(responseDTO);
        response.getWriter().write(errorResponseJson);
        log.addLogHeadBody(request, null);
        log.addLogResponse(errorResponseJson);
    }
}