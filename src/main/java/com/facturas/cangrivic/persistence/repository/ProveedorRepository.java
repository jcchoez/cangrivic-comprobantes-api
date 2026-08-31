package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ProveedorEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ProveedorRepository extends ListCrudRepository<ProveedorEntity, Integer> {
    Optional<ProveedorEntity> findByProveedorIdentificacion(String proveedorIdentificacion);
    Optional<ProveedorEntity> findByProveedorCorreo(String correo);
}