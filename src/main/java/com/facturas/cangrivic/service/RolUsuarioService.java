package com.facturas.cangrivic.service;

import com.facturas.cangrivic.persistence.entity.RolEntity;
import com.facturas.cangrivic.persistence.repository.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolUsuarioService {
    private final RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    public RolUsuarioService(RolUsuarioRepository rolUsuarioRepository) {
        this.rolUsuarioRepository = rolUsuarioRepository;
    }

    public RolEntity get(int rolId) {
        return this.rolUsuarioRepository.findById(rolId).orElse(null);
    }


    public RolEntity save(RolEntity usuario) {
        return this.rolUsuarioRepository.save(usuario);
    }

    public void delete(int usuarioId) {
        this.rolUsuarioRepository.deleteById(usuarioId);
    }

    public boolean exists(int usuarioId) {
        return this.rolUsuarioRepository.existsById(usuarioId);
    }

}
