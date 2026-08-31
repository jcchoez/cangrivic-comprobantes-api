package com.facturas.cangrivic.dto.batch.factura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDetalleDTO {
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;
}
