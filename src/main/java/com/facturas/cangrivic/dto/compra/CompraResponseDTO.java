package com.facturas.cangrivic.dto.compra;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompraResponseDTO {

    private Long compraId;
    private Integer proveedorId;
    private LocalDateTime fechaCompra;
    private Integer empresaId;
    private BigDecimal total;
    private List<CompraItemResponseDTO> items;
}