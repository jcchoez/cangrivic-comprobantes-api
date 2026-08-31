package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.proveedor.ProveedorRequestDTO;
import com.facturas.cangrivic.dto.proveedor.ProveedorResponseDTO;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.ProveedorNotFoundException;
import com.facturas.cangrivic.persistence.entity.ProveedorEntity;
import com.facturas.cangrivic.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedor")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping("/enabled")
    public ResponseEntity<Page<ProveedorEntity>> getAllEnabled(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int elements,
            @RequestParam(defaultValue = "proveedorNombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(proveedorService.getAllEnabled(page, elements, sortBy, sortDirection));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<Map<String, Object>> getAllPorEmpresaId(
            @PathVariable int empresaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements,
            @RequestParam(defaultValue = "proveedorNombre") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, elements, sort);
        Page<ProveedorResponseDTO> proveedores = proveedorService.getAllPorEmpresaId(empresaId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("proveedores", proveedores.getContent());
        response.put("currentPage", proveedores.getNumber());
        response.put("totalItems", proveedores.getTotalElements());
        response.put("totalPages", proveedores.getTotalPages());
        response.put("hasNext", proveedores.hasNext());
        response.put("hasPrevious", proveedores.hasPrevious());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{proveedorId}")
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedor(@PathVariable Integer proveedorId) {
        try {
            ProveedorResponseDTO dto = proveedorService.obtenerProveedorPorId(proveedorId);
            return ResponseEntity.ok(dto);
        } catch (ProveedorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crearProveedor(@RequestBody @Valid ProveedorRequestDTO requestDTO) {
        ProveedorResponseDTO created = proveedorService.crearProveedor(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{proveedorId}")
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(
            @PathVariable Integer proveedorId,
            @RequestBody @Valid ProveedorRequestDTO requestDTO) {
        try {
            ProveedorResponseDTO updated = proveedorService.actualizarProveedor(proveedorId, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (ProveedorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (EmpresaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{proveedorId}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Integer proveedorId) {
        try {
            proveedorService.deleteProveedor(proveedorId);
            return ResponseEntity.noContent().build();
        } catch (ProveedorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}