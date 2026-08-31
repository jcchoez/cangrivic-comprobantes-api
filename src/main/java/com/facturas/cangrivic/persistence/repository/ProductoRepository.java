package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductoRepository extends ListCrudRepository<ProductoEntity, Integer> {
}
