package com.facturas.cangrivic.dto.proveedor;

import lombok.Data;

@Data
public class ProveedorDetalleDTO {
    private Integer proveedorId;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
}