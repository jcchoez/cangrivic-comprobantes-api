package com.facturas.cangrivic.dto.compra;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompraRequestDTO {

    @NotNull(message = "El proveedor es obligatorio")
    @Min(value = 1, message = "El ID del proveedor debe ser mayor a 0")
    private Integer proveedorId;

    @NotNull(message = "El ID de la empresa es obligatorio")
    @Min(value = 1, message = "El ID de la empresa debe ser un número positivo")
    private Integer empresaId;

    private LocalDateTime fechaCompra;

    @NotNull(message = "El total de la compra es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor que 0")
    private BigDecimal total;

    @NotEmpty(message = "La compra debe tener al menos un item")
    private List<CompraItemRequestDTO> items;
}