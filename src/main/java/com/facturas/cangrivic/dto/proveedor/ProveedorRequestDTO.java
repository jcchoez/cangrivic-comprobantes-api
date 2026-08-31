package com.facturas.cangrivic.dto.proveedor;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    private Integer proveedorId; // opcional para actualizaciones

    @NotNull(message = "El nombre del proveedor no puede ser nulo")
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String proveedorNombre;

    @NotNull(message = "La identificación del proveedor no puede ser nula")
    @NotBlank(message = "La identificación del proveedor es obligatoria")
    @Size(min = 8, max = 15, message = "La identificación debe tener entre 8 y 15 caracteres")
    private String proveedorIdentificacion;

    @NotNull(message = "El teléfono del proveedor no puede ser nulo")
    @NotBlank(message = "El teléfono del proveedor es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener solo números y tener entre 7 y 15 dígitos")
    private String proveedorTelefono;

    @NotNull(message = "La dirección del proveedor no puede ser nula")
    @NotBlank(message = "La dirección del proveedor es obligatoria")
    @Size(max = 200, message = "La dirección no puede superar los 200 caracteres")
    private String proveedorDireccion;

    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 100, message = "El correo no puede tener más de 100 caracteres")
    private String proveedorCorreo;

    @NotNull(message = "El estado de desactivación no puede ser nulo")
    private Boolean proveedorDisabled = false;

    @NotNull(message = "El ID de la empresa es obligatorio")
    @Min(value = 1, message = "El ID de la empresa debe ser un número positivo")
    private Integer empresaId;
}