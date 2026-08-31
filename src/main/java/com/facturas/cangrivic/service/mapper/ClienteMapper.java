package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.cliente.ClienteRequestDTO;
import com.facturas.cangrivic.dto.cliente.ClienteResponseDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioRequestDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;

public class ClienteMapper {
    // Método para mapear de ClienteEntity a ClienteResponseDTO
    public static ClienteResponseDTO toClienteResponseDTO(ClienteEntity clienteEntity) {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setClienteId(clienteEntity.getClienteId());
        clienteResponseDTO.setClienteNombre(clienteEntity.getClienteNombre());
        clienteResponseDTO.setClienteIdentificacion(clienteEntity.getClienteIdentificacion());
        clienteResponseDTO.setClienteTelefono(clienteEntity.getClienteTelefono());
        clienteResponseDTO.setClienteDireccion(clienteEntity.getClienteDireccion());
        clienteResponseDTO.setClienteCorreo(clienteEntity.getClienteCorreo());
        clienteResponseDTO.setClienteDisabled(clienteEntity.getClienteDisabled());
        clienteResponseDTO.setEmpresaId(clienteEntity.getEmpresa().getEmpresaId());

        return clienteResponseDTO;
    }




    // Método para mapear de ClienteRequestDTO a ClienteEntity (para guardar el usuario)
    public static ClienteEntity toClienteEntity(ClienteRequestDTO clienteRequestDTO) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setClienteId(clienteRequestDTO.getClienteId());
        clienteEntity.setClienteNombre(clienteRequestDTO.getClienteNombre());
        clienteEntity.setClienteIdentificacion(clienteRequestDTO.getClienteIdentificacion());
        clienteEntity.setClienteTelefono(clienteRequestDTO.getClienteTelefono());
        clienteEntity.setClienteDireccion(clienteRequestDTO.getClienteDireccion());
        clienteEntity.setClienteCorreo(clienteRequestDTO.getClienteCorreo());
        clienteEntity.setClienteDisabled(clienteRequestDTO.getClienteDisabled());

        return clienteEntity;
    }
}
