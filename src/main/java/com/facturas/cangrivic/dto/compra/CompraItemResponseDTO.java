package com.facturas.cangrivic.dto.compra;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompraItemResponseDTO {

    private Integer productoId;
    private Integer cantidad;
    private String nombre;
    private String codigo;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}