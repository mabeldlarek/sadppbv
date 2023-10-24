package com.belarek.SADPPBV.enums;

public enum UserType {
    USER(0),
    ADM(1);

    private int tipo_usuario;

    UserType(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
