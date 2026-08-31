package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.persistence.entity.RolEntity;
import com.facturas.cangrivic.service.RolUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rol")
public class RolUsuarioController {
    private final RolUsuarioService rolUsuarioService;

    @Autowired
    public RolUsuarioController(RolUsuarioService rolUsuarioService) {
        this.rolUsuarioService = rolUsuarioService;
    }

    @GetMapping("/{rolId}")
    public ResponseEntity<RolEntity> get(@PathVariable int rolId) {
        System.out.println("Zona horaria de la JVM: " + java.util.TimeZone.getDefault().getID());
        return ResponseEntity.ok(this.rolUsuarioService.get(rolId));
    }

    @PostMapping
    public ResponseEntity<RolEntity> add(@RequestBody RolEntity rolEntity) {
        if (rolEntity.getRolId() == null || this.rolUsuarioService.exists(rolEntity.getRolId())){
            return ResponseEntity.ok(this.rolUsuarioService.save(rolEntity));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{rolId}")
    public ResponseEntity<RolEntity> delete(@PathVariable int rolId) {
        if (this.rolUsuarioService.exists(rolId)) {
            this.rolUsuarioService.delete(rolId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
