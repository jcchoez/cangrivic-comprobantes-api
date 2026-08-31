package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ProveedorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ProveedorPagSortRepository extends ListPagingAndSortingRepository<ProveedorEntity, Integer> {
    // Método con minúscula inicial (igual que en Cliente)
    Page<ProveedorEntity> findByproveedorDisabledFalse(Pageable pageable);

    Page<ProveedorEntity> findByEmpresa_EmpresaId(int empresaId, Pageable pageable);
}