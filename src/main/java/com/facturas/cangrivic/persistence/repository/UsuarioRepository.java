package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.UsuarioEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends ListCrudRepository<UsuarioEntity,Integer> {
    // Método para buscar un usuario por su username
    Optional<UsuarioEntity> findByUsuarioUsername(String username);

}
