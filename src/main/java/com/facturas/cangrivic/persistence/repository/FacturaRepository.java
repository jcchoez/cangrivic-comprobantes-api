package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, Integer> {


    // Buscar factura por número de factura
    Optional<FacturaEntity> findByFacturaNumero(String facturaNumero);

    // Buscar factura por clave de acceso
    Optional<FacturaEntity> findByFacturaClaveAcceso(String facturaClaveAcceso);

    // Obtener todas las facturas con un estado específico
    List<FacturaEntity> findByFacturaEstado(FacturaEntity.EstadoFactura estado);

    // Verificar si existe una factura con ese número
    boolean existsByFacturaNumero(String facturaNumero);
}
