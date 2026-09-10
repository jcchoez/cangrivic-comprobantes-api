package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.CompraEntity;
import com.facturas.cangrivic.persistence.entity.CompraItemEntity;
import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraItemRepository extends JpaRepository<CompraItemEntity, Long> {

    List<CompraItemEntity> findByCompra(CompraEntity compra);

    List<CompraItemEntity> findByProducto(ProductoEntity producto);

    boolean existsByItemId(Long itemId);
}