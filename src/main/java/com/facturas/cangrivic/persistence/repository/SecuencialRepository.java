package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import com.facturas.cangrivic.persistence.entity.SecuencialEntity;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para la entidad de catálogo
@Repository
public interface SecuencialRepository extends JpaRepository<SecuencialEntity, Integer> {
    Optional<SecuencialEntity> findByNombreAndEmpresaId(char nombre, Integer empresaId);

}



