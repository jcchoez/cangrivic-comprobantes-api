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



    // Eliminar item
    public void eliminarItem(Long itemId) {
        VentaItemEntity ventaItemEntity = ventaItemRepository.findById(itemId)
                .orElseThrow(() -> new VentaItemNotFoundException("Item no encontrado con ID: " + itemId));
        ventaItemRepository.delete(ventaItemEntity);
    }
}
