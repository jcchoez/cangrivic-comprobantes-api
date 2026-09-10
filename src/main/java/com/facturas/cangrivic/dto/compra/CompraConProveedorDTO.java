package com.facturas.cangrivic.dto.compra;

import com.facturas.cangrivic.dto.proveedor.ProveedorDetalleDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompraConProveedorDTO {

    private Long compraId;
    private ProveedorDetalleDTO proveedor;
    private LocalDateTime fechaCompra;
    private Integer empresaId;
    private BigDecimal total;
    private List<CompraItemResponseDTO> items;
}