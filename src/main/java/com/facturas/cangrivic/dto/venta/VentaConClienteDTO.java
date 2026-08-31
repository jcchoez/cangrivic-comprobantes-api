package com.facturas.cangrivic.dto.venta;

import com.facturas.cangrivic.dto.cliente.ClienteDetalleDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaConClienteDTO {
    private Long ventaId;
    private ClienteDetalleDTO cliente; // Objeto completo del cliente
    private LocalDateTime fechaVenta;
    private Integer empresaId;
    private BigDecimal total;
    private List<VentaItemResponseDTO> items;
}