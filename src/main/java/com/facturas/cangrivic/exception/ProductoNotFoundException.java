package com.facturas.cangrivic.exception;

public class ProductoNotFoundException extends RuntimeException{
    public ProductoNotFoundException(String message) {
        super(message);
    }
}
