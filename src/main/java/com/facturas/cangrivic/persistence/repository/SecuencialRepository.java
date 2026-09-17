package com.facturas.cangrivic.persistence.repository;
import com.facturas.cangrivic.persistence.entity.SecuencialEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para la entidad de catálogo
@Repository
public interface SecuencialRepository extends JpaRepository<SecuencialEntity, Integer> {

    // Método opcional si aún lo usas en otra parte sin bloqueo
    Optional<SecuencialEntity> findByNombreAndEmpresaId(char nombre, Integer empresaId);

    // Método con bloqueo pesimista para evitar que dos transacciones obtengan el mismo número a la vez
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SecuencialEntity s WHERE s.nombre = :nombre AND s.empresaId = :empresaId")
    Optional<SecuencialEntity> findByNombreAndEmpresaIdWithLock(
            @Param("nombre") char nombre,
            @Param("empresaId") Integer empresaId
    );
}

