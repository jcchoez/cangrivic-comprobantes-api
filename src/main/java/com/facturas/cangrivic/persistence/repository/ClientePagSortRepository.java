package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ClientePagSortRepository extends ListPagingAndSortingRepository<ClienteEntity, Integer> {
    Page<ClienteEntity> findByclienteDisabledFalse(Pageable pageable);

    Page<ClienteEntity> findByEmpresa_EmpresaId(int empresaId,Pageable pageable);

}
