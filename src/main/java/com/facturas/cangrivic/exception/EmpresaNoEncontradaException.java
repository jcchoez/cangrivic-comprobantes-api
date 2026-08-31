package com.facturas.cangrivic.exception;

public class EmpresaNoEncontradaException extends RuntimeException {
    public EmpresaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}