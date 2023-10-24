package com.belarek.SADPPBV.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO {
    private String message;
    private boolean sucess;
}
