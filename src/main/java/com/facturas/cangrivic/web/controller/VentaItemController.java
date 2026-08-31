
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

    // Obtener todos los items
    /*@GetMapping
    public ResponseEntity<List<VentaItemResponseDTO>> obtenerTodosLosItems() {
        List<VentaItemResponseDTO> items = ventaItemService.obtenerTodosLosItems();
        return ResponseEntity.ok(items);
    }

    // Obtener un item por ID
    @GetMapping("/{itemId}")
    public ResponseEntity<VentaItemResponseDTO> obtenerItemPorId(@PathVariable Long itemId) {
        try {
            VentaItemResponseDTO item = ventaItemService.obtenerItemPorId(itemId);
            return ResponseEntity.ok(item);
        } catch (VentaItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Crear un nuevo item
    @PostMapping
    public ResponseEntity<VentaItemResponseDTO> crearItem(@RequestBody @Valid VentaItemRequestDTO ventaItemRequestDTO) {
        try {
            VentaItemResponseDTO nuevoItem = ventaItemService.crearItem(ventaItemRequestDTO);
            return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    // Actualizar un item
   /* @PutMapping("/{itemId}")
    public ResponseEntity<VentaItemResponseDTO> actualizarItem(@PathVariable Long itemId,
                                                               @RequestBody @Valid VentaItemRequestDTO ventaItemRequestDTO) {
        try {
            VentaItemResponseDTO itemActualizado = ventaItemService.actualizarItem(itemId, ventaItemRequestDTO);
            return ResponseEntity.ok(itemActualizado);
        } catch (VentaItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

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
