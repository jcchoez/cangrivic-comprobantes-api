package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.VentaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Long> {

    // Buscar venta por ID del cliente
    List<VentaEntity> findByClienteId(Integer clienteId);

    // Buscar venta por fecha
    List<VentaEntity> findByFechaVenta(Date fechaVenta);

    // Buscar venta por cliente y fecha
    List<VentaEntity> findByClienteIdAndFechaVenta(Integer clienteId, Date fechaVenta);

    // Verificar si existe una venta por ID
    boolean existsByVentaId(Long ventaId);

    // Buscar una venta específica (opcional)
    Optional<VentaEntity> findByVentaId(Long ventaId);


    // Filtrar ventas por empresa y fecha exacta
    Page<VentaEntity> findByEmpresaIdAndFechaVentaGreaterThanEqualAndFechaVentaLessThan(
            Integer empresaId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            Pageable pageable);
}
