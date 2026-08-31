package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.usuario.UsuarioRequestDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;

public class UsuarioMapper {
    // Método para mapear de UsuarioEntity a UsuarioResponseDTO
    public static UsuarioResponseDTO toUsuarioResponseDTO(UsuarioEntity usuarioEntity) {
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setUsuarioId(usuarioEntity.getUsuarioId());
        usuarioResponseDTO.setUsuarioNombre(usuarioEntity.getUsuarioNombre());
        usuarioResponseDTO.setUsuarioEmail(usuarioEntity.getUsuarioEmail());
        usuarioResponseDTO.setUsuarioTelefono(usuarioEntity.getUsuarioTelefono());
        usuarioResponseDTO.setUsuarioUsername(usuarioEntity.getUsuarioUsername());
        usuarioResponseDTO.setUsuarioDisabled(usuarioEntity.getUsuarioDisabled());
        usuarioResponseDTO.setUsuarioLocked(usuarioEntity.getUsuarioLocked());
        usuarioResponseDTO.setEmpresaId(usuarioEntity.getEmpresa().getEmpresaId());

        return usuarioResponseDTO;
    }

    // Método para mapear de UsuarioRequestDTO a UsuarioEntity (para guardar el usuario)
    public static UsuarioEntity toUsuarioEntity(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsuarioId(usuarioRequestDTO.getUsuarioId());
        usuarioEntity.setUsuarioNombre(usuarioRequestDTO.getUsuarioNombre());
        usuarioEntity.setUsuarioEmail(usuarioRequestDTO.getUsuarioEmail());
        usuarioEntity.setUsuarioTelefono(usuarioRequestDTO.getUsuarioTelefono());
        usuarioEntity.setUsuarioUsername(usuarioRequestDTO.getUsuarioUsername());
        usuarioEntity.setUsuarioPassword(usuarioRequestDTO.getUsuarioPassword());
        usuarioEntity.setUsuarioDisabled(usuarioRequestDTO.getUsuarioDisabled());
        usuarioEntity.setUsuarioLocked(usuarioRequestDTO.getUsuarioLocked());

        return usuarioEntity;
    }

}
