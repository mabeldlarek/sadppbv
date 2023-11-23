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
import java.util.StringJoiner;

@Component
public class RegistrarLogsRequestResponse {
    private static final Logger logger = LoggerFactory.getLogger(RegistrarLogsRequestResponse.class);
    private static StringJoiner headersString = new StringJoiner(", ");
    public static void addLogHeadBody(HttpServletRequest request, Object object){
        logger.info("RECEBIDO:");
        Enumeration<String> headerNames = request.getHeaderNames();
        String cabecalho = "";
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            cabecalho = headerName + " = " + headerValue ;
            logger.info( cabecalho);
        }

        String requestBody = new String(String.valueOf(object));
        logger.info("\nCorpo da Mensagem/variável da Requisição: " + requestBody);
    }

    public static void addLogResponse(Object response){
        logger.info("Resposta:" + response);
    }
}
