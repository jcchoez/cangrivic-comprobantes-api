package com.facturas.cangrivic.web.advice;

import com.facturas.cangrivic.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // -------------------
    // Helper para JSON uniforme
    // -------------------
    private Map<String, Object> buildError(HttpStatus status, String message, Map<String, String> errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("errors", errors != null ? errors : new HashMap<>());
        return body;
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(buildError(status, message, null), status);
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpStatus status, String message, Map<String, String> errors) {
        return new ResponseEntity<>(buildError(status, message, errors), status);
    }




    // -------------------
    // Manejo de errores de validación (@Valid)
    // -------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errorResponse(HttpStatus.BAD_REQUEST, "Errores de validación", errors);
    }

    // -------------------
    // Handlers individuales de NotFound
    // -------------------
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotFoundException(UsuarioNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // -------------------
    // Manejo de ClienteAlreadyExistsException
    // -------------------
    @ExceptionHandler(ClienteAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleClienteAlreadyExistsException(ClienteAlreadyExistsException ex) {
        return errorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }


    @ExceptionHandler(EmpresaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEmpresaNotFoundException(EmpresaNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RolNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRolNotFoundException(RolNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNotFoundException(ClienteNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SecuencialesNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSecuencialesNotFoundException(SecuencialesNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductoNotFoundException(ProductoNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // -------------------
    // Manejo de integridad de datos
    // -------------------
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST,
                "Error de integridad de datos. La operación no se puede completar.");
    }

    // -------------------
    // Manejo genérico para cualquier otra excepción
    // -------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage() != null ? ex.getMessage() : "Ha ocurrido un error inesperado");
    }
}
