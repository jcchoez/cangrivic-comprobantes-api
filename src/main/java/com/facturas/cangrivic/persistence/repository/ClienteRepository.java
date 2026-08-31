package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ClienteRepository extends ListCrudRepository<ClienteEntity, Integer> {
    Optional<ClienteEntity> findByClienteIdentificacion(String clienteIdentificacion);
    Optional<ClienteEntity> findByClienteCorreo(String correo);
}
