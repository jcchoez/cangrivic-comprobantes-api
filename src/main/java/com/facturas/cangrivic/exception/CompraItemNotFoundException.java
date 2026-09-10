package com.facturas.cangrivic.exception;

public class CompraItemNotFoundException extends RuntimeException {
    public CompraItemNotFoundException(String message) {
        super(message);
    }
}