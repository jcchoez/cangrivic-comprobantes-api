package com.facturas.cangrivic.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UsuarioRequestDTO {
    //@NotNull(message = "El ID de usuario no puede ser nulo")
    private Integer usuarioId;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String usuarioNombre;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String usuarioEmail;

    @NotNull(message = "El numero de celular no debe ser vacio")
    private String usuarioTelefono;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    @Size(min = 3, max = 150, message = "El nombre de usuario debe tener entre 3 y 150 caracteres")
    private String usuarioUsername;

    @NotNull(message = "El nombre de usuario no puede ser nulo")
    private String usuarioPassword;

    @NotNull(message = "El estado de desactivación no puede ser nulo")
    private Boolean usuarioDisabled;

    @NotNull(message = "El estado de bloqueo no puede ser nulo")
    private Boolean usuarioLocked;

    @NotNull(message = "El ID de la empresa no puede ser nulo")
    private Integer empresaId;

    @NotNull(message = "El ID del rol  no puede ser nulo")
    private Integer rolId;
}
