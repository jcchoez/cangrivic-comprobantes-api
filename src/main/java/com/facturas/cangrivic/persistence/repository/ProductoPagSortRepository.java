package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ProductoPagSortRepository extends ListPagingAndSortingRepository<ProductoEntity, Integer> {
    Page<ProductoEntity> findByproductoDisabledFalse(Pageable pageable);

    Page<ProductoEntity> findByEmpresa_EmpresaId(int empresaId,Pageable pageable);
}
