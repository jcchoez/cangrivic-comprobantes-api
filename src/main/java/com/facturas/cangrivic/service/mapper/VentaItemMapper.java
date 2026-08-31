package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.venta.VentaItemRequestDTO;
import com.facturas.cangrivic.persistence.entity.VentaItemEntity;

public class VentaItemMapper {

    public static VentaItemEntity toVentaItemEntity(VentaItemRequestDTO requestDTO) {
        VentaItemEntity entity = new VentaItemEntity();
        entity.setCantidad(requestDTO.getCantidad());
        entity.setPrecioUnitario(requestDTO.getPrecioUnitario());
        entity.setSubtotal(requestDTO.getSubtotal());
        // NOTA: producto y venta se asignan en el servicio
        return entity;
    }
}