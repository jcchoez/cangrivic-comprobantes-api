package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.FacturaDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaDetalleRepository extends JpaRepository<FacturaDetalleEntity, Integer> {
    // Puedes agregar métodos personalizados si lo necesitas
}
