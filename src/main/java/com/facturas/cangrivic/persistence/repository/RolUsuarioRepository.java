package com.facturas.cangrivic.persistence.repository;

import com.facturas.cangrivic.persistence.entity.RolEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface RolUsuarioRepository  extends ListCrudRepository<RolEntity, Integer> {
}
