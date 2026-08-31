package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.VentaItemEntity;
import com.facturas.cangrivic.persistence.entity.VentaEntity;
import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaItemRepository extends JpaRepository<VentaItemEntity, Long> {

    // Obtener todos los items de una venta
    List<VentaItemEntity> findByVenta(VentaEntity venta);

    // Obtener todos los items de un producto
    List<VentaItemEntity> findByProducto(ProductoEntity producto);

    // Verificar si existe un item por ID
    boolean existsByItemId(Long itemId);
}
