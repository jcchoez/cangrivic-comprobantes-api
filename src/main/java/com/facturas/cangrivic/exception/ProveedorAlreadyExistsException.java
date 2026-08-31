package com.facturas.cangrivic.exception;

public class ProveedorAlreadyExistsException extends RuntimeException {

    public ProveedorAlreadyExistsException(String message) {
        super(message);
    }

    public ProveedorAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}