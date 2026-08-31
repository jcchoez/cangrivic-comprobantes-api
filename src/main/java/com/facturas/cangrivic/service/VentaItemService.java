package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.venta.VentaItemRequestDTO;
import com.facturas.cangrivic.dto.venta.VentaItemResponseDTO;
import com.facturas.cangrivic.exception.VentaItemNotFoundException;
import com.facturas.cangrivic.persistence.entity.VentaItemEntity;
import com.facturas.cangrivic.persistence.repository.VentaItemRepository;
import com.facturas.cangrivic.service.mapper.VentaItemMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaItemService {

    private final VentaItemRepository ventaItemRepository;

    public VentaItemService(VentaItemRepository ventaItemRepository) {
        this.ventaItemRepository = ventaItemRepository;
    }

    // Obtener todos los items
    /*public List<VentaItemResponseDTO> obtenerTodosLosItems() {
        return ventaItemRepository.findAll()
                .stream()
                .map(VentaItemMapper::toVentaItemResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener item por ID
    public VentaItemResponseDTO obtenerItemPorId(Long itemId) {
        VentaItemEntity ventaItemEntity = ventaItemRepository.findById(itemId)
                .orElseThrow(() -> new VentaItemNotFoundException("Item no encontrado con ID: " + itemId));
        return VentaItemMapper.toVentaItemResponseDTO(ventaItemEntity);
    }

    // Crear nuevo item
    public VentaItemResponseDTO crearItem(VentaItemRequestDTO ventaItemRequestDTO) {
        VentaItemEntity ventaItemEntity = VentaItemMapper.toVentaItemEntity(ventaItemRequestDTO,null);
        VentaItemEntity savedItem = ventaItemRepository.save(ventaItemEntity);
        return VentaItemMapper.toVentaItemResponseDTO(savedItem);
    }*/

    // Actualizar item
   /* public VentaItemResponseDTO actualizarItem(Long itemId, VentaItemRequestDTO ventaItemRequestDTO) {
        VentaItemEntity ventaItemEntity = ventaItemRepository.findById(itemId)
                .orElseThrow(() -> new VentaItemNotFoundException("Item no encontrado con ID: " + itemId));

        if (ventaItemRequestDTO.getVentaId() != null) {
            ventaItemEntity.getVenta().setVentaId(ventaItemRequestDTO.getVentaId());
        }

        if (ventaItemRequestDTO.getProductoId() != null) {
            ventaItemEntity.getProducto().setProductoId(ventaItemRequestDTO.getProductoId());
        }

        if (ventaItemRequestDTO.getCantidad() != null) {
            ventaItemEntity.setCantidad(ventaItemRequestDTO.getCantidad());
        }

        if (ventaItemRequestDTO.getPrecioUnitario() != null) {
            ventaItemEntity.setPrecioUnitario(ventaItemRequestDTO.getPrecioUnitario());
        }

        if (ventaItemRequestDTO.getSubtotal() != null) {
            ventaItemEntity.setSubtotal(ventaItemRequestDTO.getSubtotal());
        }

        VentaItemEntity updatedItem = ventaItemRepository.save(ventaItemEntity);
        return VentaItemMapper.toVentaItemResponseDTO(updatedItem);
    }*/

    // Eliminar item
    public void eliminarItem(Long itemId) {
        VentaItemEntity ventaItemEntity = ventaItemRepository.findById(itemId)
                .orElseThrow(() -> new VentaItemNotFoundException("Item no encontrado con ID: " + itemId));
        ventaItemRepository.delete(ventaItemEntity);
    }
}
