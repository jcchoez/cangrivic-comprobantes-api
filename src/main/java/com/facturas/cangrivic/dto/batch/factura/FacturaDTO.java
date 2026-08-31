package com.facturas.cangrivic.dto.batch.factura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {
    private String facturaNumero;
    private String facturaClaveAcceso;
    private Double facturaTotal;
    private Date facturaFecha;
    private Integer empresaId;
    private Integer usuarioId;
    private Integer clienteId;
    private List<FacturaDetalleDTO> detalles;
}
