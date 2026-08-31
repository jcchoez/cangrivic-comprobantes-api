package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.SecuencialLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio para la entidad de logs
@Repository
public interface SecuencialLogRepository extends JpaRepository<SecuencialLogEntity, Long> {
}