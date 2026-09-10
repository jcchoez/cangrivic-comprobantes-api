package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.exception.CompraItemNotFoundException;
import com.facturas.cangrivic.service.CompraItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compra-items")
public class CompraItemController {

    private final CompraItemService compraItemService;

    public CompraItemController(CompraItemService compraItemService) {
        this.compraItemService = compraItemService;
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long itemId) {
        try {
            compraItemService.eliminarItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (CompraItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}