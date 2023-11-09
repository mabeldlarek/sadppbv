package com.belarek.SADPPBV.util;

import com.belarek.SADPPBV.SadppbvApplication;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.Enumeration;

@Component
public class RegistrarLogsRequestResponse {
    private static final Logger logger = LoggerFactory.getLogger(RegistrarLogsRequestResponse.class);

    public static void addLogHeadBody(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.info("Cabeçalho: " + headerName + " = " + headerValue);
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        byte[] content = wrappedRequest.getContentAsByteArray();
        String requestBody = new String(content);
        logger.info("Corpo da Mensagem da Requisição: " + requestBody);
    }

    public static void addLogResponse(Object response){
        logger.info("Resposta:" + response);
    }
}
