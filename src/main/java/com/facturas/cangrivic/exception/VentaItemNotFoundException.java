package com.facturas.cangrivic.exception;

public class VentaItemNotFoundException extends RuntimeException{
    public VentaItemNotFoundException(String message) {
        super(message);
    }
}
