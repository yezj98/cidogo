package com.codigo_app.Service;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}