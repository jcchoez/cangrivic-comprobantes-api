package com.facturas.cangrivic.dto.cliente;

import lombok.Data;

@Data
public class ClienteDetalleDTO {
    private Integer clienteId;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    // Agrega otros campos que necesites
}