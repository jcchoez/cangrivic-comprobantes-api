package com.facturas.cangrivic.dto.cliente;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteResponseDTO {

    private Integer clienteId;

    @NotNull(message = "El nombre del cliente no puede ser nulo")
    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String clienteNombre;

    @NotNull(message = "La identificación del cliente no puede ser nula")
    @NotBlank(message = "La identificación del cliente es obligatoria")
    @Size(min = 8, max = 15, message = "La identificación debe tener entre 8 y 15 caracteres")
    private String clienteIdentificacion;

    @NotNull(message = "El teléfono del cliente no puede ser nulo")
    @NotBlank(message = "El teléfono del cliente es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener solo números y tener entre 7 y 15 dígitos")
    private String clienteTelefono;

    @NotNull(message = "La dirección del cliente no puede ser nula")
    @NotBlank(message = "La dirección del cliente es obligatoria")
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String clienteDireccion;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 100, message = "El correo no puede tener más de 100 caracteres")
    private String clienteCorreo; // ← Nuevo campo agregado

    @NotNull(message = "El estado de desactivación no puede ser nulo")
    private Boolean clienteDisabled = false;

    @NotNull(message = "El ID de la empresa es obligatorio")
    @Min(value = 1, message = "El ID de la empresa debe ser un número positivo")
    private Integer empresaId;
}
