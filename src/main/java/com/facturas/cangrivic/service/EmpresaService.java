package com.facturas.cangrivic.service;


import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public EmpresaEntity get(int empresaId){
        return this.empresaRepository.findById(empresaId).orElse(null);
    }

    public EmpresaEntity save(EmpresaEntity empresaEntity) {
        return this.empresaRepository.save(empresaEntity);
    }

    public void delete(int empresaId) {
        this.empresaRepository.deleteById(empresaId);
    }

    public boolean exists(int empresaId) {
        return this.empresaRepository.existsById(empresaId);
    }


}
