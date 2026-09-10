package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.CompraEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, Long> {

    List<CompraEntity> findByProveedorId(Integer proveedorId);

    List<CompraEntity> findByFechaCompra(LocalDateTime fechaCompra);

    List<CompraEntity> findByProveedorIdAndFechaCompra(Integer proveedorId, LocalDateTime fechaCompra);

    boolean existsByCompraId(Long compraId);

    Optional<CompraEntity> findByCompraId(Long compraId);

    Page<CompraEntity> findByEmpresaIdAndFechaCompraGreaterThanEqualAndFechaCompraLessThan(
            Integer empresaId,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            Pageable pageable);
}