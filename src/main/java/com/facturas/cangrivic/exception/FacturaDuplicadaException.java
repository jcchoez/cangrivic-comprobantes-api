package com.facturas.cangrivic.exception;

public class FacturaDuplicadaException extends RuntimeException {
    public FacturaDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
