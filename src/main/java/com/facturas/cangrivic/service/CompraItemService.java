package com.facturas.cangrivic.service;

import com.facturas.cangrivic.exception.CompraItemNotFoundException;
import com.facturas.cangrivic.persistence.entity.CompraItemEntity;
import com.facturas.cangrivic.persistence.repository.CompraItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CompraItemService {

    private final CompraItemRepository compraItemRepository;

    public CompraItemService(CompraItemRepository compraItemRepository) {
        this.compraItemRepository = compraItemRepository;
    }

    public void eliminarItem(Long itemId) {
        CompraItemEntity entity = compraItemRepository.findById(itemId)
                .orElseThrow(() -> new CompraItemNotFoundException("Item no encontrado con ID: " + itemId));
        compraItemRepository.delete(entity);
    }
}