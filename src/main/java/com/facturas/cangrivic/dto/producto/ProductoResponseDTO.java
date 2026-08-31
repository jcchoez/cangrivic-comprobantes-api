package com.facturas.cangrivic.dto.producto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Integer productoId;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 100, message = "El nombre del producto no puede exceder los 100 caracteres")
    private String productoNombre;

    @NotBlank(message = "El código del producto no puede estar vacío")
    @Size(max = 50, message = "El código del producto no puede exceder los 50 caracteres")
    private String productoCodigo;

    @Size(max = 255, message = "La descripción del producto no puede exceder los 255 caracteres")
    private String productoDescripcion;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private Double productoPrecio;

    @NotNull(message = "El stock del producto es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer productoStock = 0;

    @NotNull(message = "El estado del producto es obligatorio")
    private Boolean productoEstado = true;

    @NotNull(message = "El estado disable del producto es obligatorio")
    private Boolean productoDisabled = true;

    @NotNull(message = "El ID de la empresa no puede ser nulo")
    private Integer empresaId;
}
