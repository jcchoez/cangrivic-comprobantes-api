package com.facturas.cangrivic.dto.venta;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaRequestDTO {

    // ❌ ELIMINAR: private Long ventaId; (se genera automáticamente)

    @NotNull(message = "El cliente es obligatorio")
    @Min(value = 1, message = "El ID del cliente debe ser mayor a 0")
    private Integer clienteId;

    @NotNull(message = "El ID de la empresa es obligatorio")
    @Min(value = 1, message = "El ID de la empresa debe ser un número positivo")
    private Integer empresaId;


    // Fecha de la venta (opcional, se asigna si no se envía)
    private LocalDateTime fechaVenta;

    @NotNull(message = "El total de la venta es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor que 0")
    private BigDecimal total;

    // Lista de items de la venta
    @NotEmpty(message = "La venta debe tener al menos un item")
    private List<VentaItemRequestDTO> items;
}
