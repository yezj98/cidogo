package com.codigo_app.Service;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
