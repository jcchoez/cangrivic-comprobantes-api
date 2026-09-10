package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.compra.CompraConProveedorDTO;
import com.facturas.cangrivic.dto.compra.CompraRequestDTO;
import com.facturas.cangrivic.dto.compra.CompraResponseDTO;
import com.facturas.cangrivic.exception.CompraNotFoundException;
import com.facturas.cangrivic.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> getAllCompras() {
        return ResponseEntity.ok(compraService.obtenerTodasLasCompras());
    }

    @GetMapping("/{compraId}")
    public ResponseEntity<CompraResponseDTO> getCompraById(@PathVariable Long compraId) {
        try {
            return ResponseEntity.ok(compraService.obtenerCompraPorId(compraId));
        } catch (CompraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> crearCompra(@RequestBody @Valid CompraRequestDTO requestDTO) {
        CompraResponseDTO response = compraService.crearCompra(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{compraId}")
    public ResponseEntity<CompraResponseDTO> actualizarCompra(@PathVariable Long compraId,
                                                              @RequestBody @Valid CompraRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(compraService.actualizarCompra(compraId, requestDTO));
        } catch (CompraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{compraId}")
    public ResponseEntity<Void> eliminarCompra(@PathVariable Long compraId) {
        try {
            compraService.eliminarCompra(compraId);
            return ResponseEntity.noContent().build();
        } catch (CompraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/empresa/{empresaId}/rango")
    public ResponseEntity<Map<String, Object>> getComprasPorRango(
            @PathVariable Integer empresaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCompra") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CompraConProveedorDTO> comprasPage = compraService.getComprasPorRangoPaginado(empresaId, fechaDesde, fechaHasta, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("compras", comprasPage.getContent());
        response.put("currentPage", comprasPage.getNumber());
        response.put("totalItems", comprasPage.getTotalElements());
        response.put("totalPages", comprasPage.getTotalPages());
        response.put("hasNext", comprasPage.hasNext());
        response.put("hasPrevious", comprasPage.hasPrevious());

        return ResponseEntity.ok(response);
    }
}