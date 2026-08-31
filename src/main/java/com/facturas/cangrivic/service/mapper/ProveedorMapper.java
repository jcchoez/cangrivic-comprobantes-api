package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.proveedor.ProveedorRequestDTO;
import com.facturas.cangrivic.dto.proveedor.ProveedorResponseDTO;
import com.facturas.cangrivic.persistence.entity.ProveedorEntity;

public class ProveedorMapper {

    public static ProveedorResponseDTO toProveedorResponseDTO(ProveedorEntity entity) {
        if (entity == null) return null;
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setProveedorId(entity.getProveedorId());
        dto.setProveedorNombre(entity.getProveedorNombre());
        dto.setProveedorIdentificacion(entity.getProveedorIdentificacion());
        dto.setProveedorTelefono(entity.getProveedorTelefono());
        dto.setProveedorDireccion(entity.getProveedorDireccion());
        dto.setProveedorCorreo(entity.getProveedorCorreo());
        dto.setProveedorDisabled(entity.getProveedorDisabled());
        dto.setEmpresaId(entity.getEmpresa() != null ? entity.getEmpresa().getEmpresaId() : null);
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaModificacion(entity.getFechaModificacion());
        return dto;
    }

    public static ProveedorEntity toProveedorEntity(ProveedorRequestDTO dto) {
        if (dto == null) return null;
        ProveedorEntity entity = new ProveedorEntity();
        entity.setProveedorId(dto.getProveedorId());
        entity.setProveedorNombre(dto.getProveedorNombre());
        entity.setProveedorIdentificacion(dto.getProveedorIdentificacion());
        entity.setProveedorTelefono(dto.getProveedorTelefono());
        entity.setProveedorDireccion(dto.getProveedorDireccion());
        entity.setProveedorCorreo(dto.getProveedorCorreo());
        entity.setProveedorDisabled(dto.getProveedorDisabled() != null ? dto.getProveedorDisabled() : false);
        return entity;
    }
}