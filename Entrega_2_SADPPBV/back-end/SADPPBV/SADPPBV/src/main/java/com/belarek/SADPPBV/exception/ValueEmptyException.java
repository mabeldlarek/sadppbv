package com.belarek.SADPPBV.exception;

public class ValueEmptyException extends RuntimeException{
    public ValueEmptyException(String campo) {
        super("Valor do campo " + campo + " não pode ser vazio.");
    }
}
