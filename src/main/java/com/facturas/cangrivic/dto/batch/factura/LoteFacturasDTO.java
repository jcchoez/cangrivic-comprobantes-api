package com.facturas.cangrivic.dto.batch.factura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteFacturasDTO {
    private String loteId;
    private List<FacturaDTO> facturas;
}