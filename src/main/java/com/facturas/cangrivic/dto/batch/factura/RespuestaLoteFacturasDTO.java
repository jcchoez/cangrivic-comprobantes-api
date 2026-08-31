package com.facturas.cangrivic.dto.batch.factura;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RespuestaLoteFacturasDTO {
    private List<String> facturasProcesadas;
    private List<String> errores;
}