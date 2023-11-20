package com.belarek.SADPPBV.exception;

import com.belarek.SADPPBV.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseDTO> handleValueEmptyException(MethodArgumentNotValidException e) {
        ResponseDTO response = new ResponseDTO();
        response.setMessage(e.getAllErrors().get(0).getDefaultMessage());
        response.setSuccess(false);
        return ResponseEntity.status(403).body(response);
    }
}
