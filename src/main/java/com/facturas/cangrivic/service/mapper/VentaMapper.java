package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.venta.VentaItemResponseDTO;
import com.facturas.cangrivic.dto.venta.VentaRequestDTO;
import com.facturas.cangrivic.dto.venta.VentaResponseDTO;
import com.facturas.cangrivic.persistence.entity.VentaEntity;
import com.facturas.cangrivic.service.ClienteService;

import java.util.List;
import java.util.stream.Collectors;

public class VentaMapper {


    private static ClienteService clienteService;



    public static VentaResponseDTO toVentaResponseDTO(VentaEntity ventaEntity) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setEmpresaId(ventaEntity.getEmpresaId());
        dto.setVentaId(ventaEntity.getVentaId());
        dto.setClienteId(ventaEntity.getClienteId());
        dto.setFechaVenta(ventaEntity.getFechaVenta());
        dto.setTotal(ventaEntity.getTotal());

        // ✅ INCLUIR LOS ITEMS EN LA RESPUESTA
        if (ventaEntity.getItems() != null) {
            List<VentaItemResponseDTO> itemsDTO = ventaEntity.getItems()
                    .stream()
                    .map(item -> {
                        VentaItemResponseDTO itemDTO = new VentaItemResponseDTO();
                        //itemDTO.setItemId(item.getItemId());
                        itemDTO.setProductoId(item.getProducto().getProductoId());
                        itemDTO.setCantidad(item.getCantidad());
                        itemDTO.setPrecioUnitario(item.getPrecioUnitario());
                        itemDTO.setSubtotal(item.getSubtotal());
                        return itemDTO;
                    })
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }


        return dto;
    }


    public static VentaEntity toVentaEntity(VentaRequestDTO dto) {
        VentaEntity entity = new VentaEntity();
        entity.setEmpresaId(dto.getEmpresaId());
        entity.setClienteId(dto.getClienteId());
        entity.setFechaVenta(dto.getFechaVenta());
        entity.setTotal(dto.getTotal());
        return entity;
    }
}