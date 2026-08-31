package com.facturas.cangrivic.dto.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    //@NotNull(message = "El ID de usuario no puede ser nulo")
    private Integer usuarioId;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String usuarioNombre;

    @NotNull(message = "El email de usuario no puede ser nulo")
    private String usuarioEmail;

    @NotNull(message = "El numero de celular no debe ser vacio")
    private String usuarioTelefono;

    @NotNull(message = "El username de usuario no puede ser nulo")
    private String usuarioUsername;

    private Boolean usuarioDisabled;

    private Boolean usuarioLocked;

    @NotNull(message = "El ID de la empresa no puede ser nulo")
    private Integer empresaId;

}
