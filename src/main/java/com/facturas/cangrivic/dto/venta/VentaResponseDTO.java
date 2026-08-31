package com.facturas.cangrivic.dto.venta;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {

    private Long ventaId;
    private Integer clienteId;
    private LocalDateTime fechaVenta;
    private Integer empresaId;
    private BigDecimal total;

    // Incluye el detalle de items de la venta
    private List<VentaItemResponseDTO> items;
}
