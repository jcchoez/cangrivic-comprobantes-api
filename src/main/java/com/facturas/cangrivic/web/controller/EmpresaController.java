package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import com.facturas.cangrivic.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<EmpresaEntity> get(@PathVariable int empresaId) {
        return ResponseEntity.ok(this.empresaService.get(empresaId));
    }

    @PostMapping
    public ResponseEntity<EmpresaEntity> add(@RequestBody EmpresaEntity empresaEntity) {
        if(empresaEntity.getEmpresaId() ==  null || this.empresaService.exists(empresaEntity.getEmpresaId())) {
            return ResponseEntity.ok(this.empresaService.save(empresaEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<EmpresaEntity> update(@RequestBody EmpresaEntity empresaEntity) {
        if(empresaEntity.getEmpresaId() ==  null || this.empresaService.exists(empresaEntity.getEmpresaId())) {
            return ResponseEntity.ok(this.empresaService.save(empresaEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{empresaId}")
    public ResponseEntity<EmpresaEntity> delete (@PathVariable int empresaId) {
        if (this.empresaService.exists(empresaId)) {
            this.empresaService.delete(empresaId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }




}
