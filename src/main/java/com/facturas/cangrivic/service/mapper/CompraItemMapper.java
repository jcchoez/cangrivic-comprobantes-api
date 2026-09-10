package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.compra.CompraItemRequestDTO;
import com.facturas.cangrivic.persistence.entity.CompraItemEntity;

public class CompraItemMapper {

    public static CompraItemEntity toCompraItemEntity(CompraItemRequestDTO requestDTO) {
        CompraItemEntity entity = new CompraItemEntity();
        entity.setCantidad(requestDTO.getCantidad());
        entity.setPrecioUnitario(requestDTO.getPrecioUnitario());
        entity.setSubtotal(requestDTO.getSubtotal());
        // producto y compra se asignan en el servicio
        return entity;
    }
}