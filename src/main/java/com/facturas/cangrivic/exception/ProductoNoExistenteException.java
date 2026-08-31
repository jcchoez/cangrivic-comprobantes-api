package com.facturas.cangrivic.exception;

public class ProductoNoExistenteException extends RuntimeException {
    public ProductoNoExistenteException(String mensaje) {
        super(mensaje);
    }
}