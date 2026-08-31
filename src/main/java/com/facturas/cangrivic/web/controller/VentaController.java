package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.venta.VentaConClienteDTO;
import com.facturas.cangrivic.dto.venta.VentaRequestDTO;
import com.facturas.cangrivic.dto.venta.VentaResponseDTO;
import com.facturas.cangrivic.exception.VentaNotFoundException;
import com.facturas.cangrivic.service.VentaService;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;



    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    // Listar todas las ventas
    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> getAllVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodasLasVentas());
    }

    // Obtener venta por ID
    @GetMapping("/{ventaId}")
    public ResponseEntity<VentaResponseDTO> getVentaById(@PathVariable Long ventaId) {
        try {
            return ResponseEntity.ok(ventaService.obtenerVentaPorId(ventaId));
        } catch (VentaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Crear nueva venta
    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@RequestBody @Valid VentaRequestDTO ventaRequestDTO) {
        VentaResponseDTO response = ventaService.crearVenta(ventaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Actualizar venta
    @PutMapping("/{ventaId}")
    public ResponseEntity<VentaResponseDTO> actualizarVenta(@PathVariable Long ventaId,
                                                            @RequestBody @Valid VentaRequestDTO ventaRequestDTO) {
        try {
            return ResponseEntity.ok(ventaService.actualizarVenta(ventaId, ventaRequestDTO));
        } catch (VentaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar venta
    @DeleteMapping("/{ventaId}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long ventaId) {
        try {
            ventaService.eliminarVenta(ventaId);
            return ResponseEntity.noContent().build();
        } catch (VentaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }






    @GetMapping("/empresa/{empresaId}/rango")
    public ResponseEntity<Map<String, Object>> getVentasPorRango(
            @PathVariable Integer empresaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaVenta") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // ✅ Cambiado: Ahora usa VentaConClienteDTO en lugar de VentaResponseDTO
        Page<VentaConClienteDTO> ventasPage = ventaService.getVentasPorRangoPaginado(empresaId, fechaDesde, fechaHasta, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("ventas", ventasPage.getContent());
        response.put("currentPage", ventasPage.getNumber());
        response.put("totalItems", ventasPage.getTotalElements());
        response.put("totalPages", ventasPage.getTotalPages());
        response.put("hasNext", ventasPage.hasNext());
        response.put("hasPrevious", ventasPage.hasPrevious());

        return ResponseEntity.ok(response);
    }

    /*
    @GetMapping("/empresa/{empresaId}/rango")
    public ResponseEntity<Map<String, Object>> getVentasConClientePorRango(
            @PathVariable Integer empresaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaVenta") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Llamada al service que ya devuelve VentaConClienteDTO con cliente incluido
        Page<VentaConClienteDTO> ventasPage = ventaService.getVentasConClientePorRango(
                empresaId, fechaDesde, fechaHasta, pageable
        );

        Map<String, Object> response = new HashMap<>();
        response.put("ventas", ventasPage.getContent());
        response.put("currentPage", ventasPage.getNumber());
        response.put("totalItems", ventasPage.getTotalElements());
        response.put("totalPages", ventasPage.getTotalPages());
        response.put("hasNext", ventasPage.hasNext());
        response.put("hasPrevious", ventasPage.hasPrevious());

        return ResponseEntity.ok(response);
    }*/
}
