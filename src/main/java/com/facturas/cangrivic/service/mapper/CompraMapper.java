package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.compra.CompraItemResponseDTO;
import com.facturas.cangrivic.dto.compra.CompraRequestDTO;
import com.facturas.cangrivic.dto.compra.CompraResponseDTO;
import com.facturas.cangrivic.persistence.entity.CompraEntity;
import com.facturas.cangrivic.persistence.entity.CompraItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CompraMapper {

    public static CompraResponseDTO toCompraResponseDTO(CompraEntity entity) {
        CompraResponseDTO dto = new CompraResponseDTO();
        dto.setCompraId(entity.getCompraId());
        dto.setProveedorId(entity.getProveedorId());
        dto.setFechaCompra(entity.getFechaCompra());
        dto.setEmpresaId(entity.getEmpresaId());
        dto.setTotal(entity.getTotal());

        if (entity.getItems() != null) {
            List<CompraItemResponseDTO> itemsDTO = entity.getItems()
                    .stream()
                    .map(item -> {
                        CompraItemResponseDTO itemDTO = new CompraItemResponseDTO();
                        itemDTO.setProductoId(item.getProducto().getProductoId());
                        itemDTO.setCantidad(item.getCantidad());
                        itemDTO.setPrecioUnitario(item.getPrecioUnitario());
                        itemDTO.setSubtotal(item.getSubtotal());
                        if (item.getProducto() != null) {
                            itemDTO.setNombre(item.getProducto().getProductoNombre());
                            itemDTO.setCodigo(item.getProducto().getProductoCodigo());
                        }
                        return itemDTO;
                    })
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }
        return dto;
    }

    public static CompraEntity toCompraEntity(CompraRequestDTO dto) {
        CompraEntity entity = new CompraEntity();
        entity.setEmpresaId(dto.getEmpresaId());
        entity.setProveedorId(dto.getProveedorId());
        entity.setFechaCompra(dto.getFechaCompra());
        entity.setTotal(dto.getTotal());
        return entity;
    }
}