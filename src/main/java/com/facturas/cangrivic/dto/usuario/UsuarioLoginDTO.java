package com.facturas.cangrivic.dto.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioLoginDTO {

    @NotNull(message = "El username no puede ser nulo")
    private  String usuarioUsername;

    @NotNull(message = "La contraseña no puede ser nula")
    private String usuarioPassword;

}
