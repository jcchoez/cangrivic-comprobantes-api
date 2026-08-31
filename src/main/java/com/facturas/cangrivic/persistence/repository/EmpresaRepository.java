package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface EmpresaRepository extends ListCrudRepository<EmpresaEntity, Integer> {
}
