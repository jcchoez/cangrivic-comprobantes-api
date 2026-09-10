
package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.venta.VentaItemRequestDTO;
import com.facturas.cangrivic.dto.venta.VentaItemResponseDTO;
import com.facturas.cangrivic.exception.VentaItemNotFoundException;
import com.facturas.cangrivic.service.VentaItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venta-items")
public class VentaItemController {

    private final VentaItemService ventaItemService;

    @Autowired
    public VentaItemController(VentaItemService ventaItemService) {
        this.ventaItemService = ventaItemService;
    }


    // Eliminar un item
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        try {
            ventaItemService.eliminarItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (VentaItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
