package com.facturas.cangrivic.dto.venta;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VentaItemResponseDTO {

    /*private Long itemId;*/
    /*private Long ventaId;*/
    private Integer productoId;
    private Integer cantidad;
    private String nombre;
    private String codigo;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
