package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.cliente.ClienteRequestDTO;
import com.facturas.cangrivic.dto.cliente.ClienteResponseDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioRequestDTO;
import com.facturas.cangrivic.dto.usuario.UsuarioResponseDTO;
import com.facturas.cangrivic.exception.*;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import com.facturas.cangrivic.persistence.entity.RolEntity;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;
import com.facturas.cangrivic.persistence.repository.ClientePagSortRepository;
import com.facturas.cangrivic.persistence.repository.ClienteRepository;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import com.facturas.cangrivic.service.mapper.ClienteMapper;
import com.facturas.cangrivic.service.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClientePagSortRepository clientePagSortRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClientePagSortRepository clientePagSortRepository, EmpresaRepository empresaRepository) {
        this.clienteRepository = clienteRepository;
        this.clientePagSortRepository = clientePagSortRepository;
        this.empresaRepository = empresaRepository;
    }

    public ClienteEntity get(int clienteId) {
        return this.clienteRepository.findById(clienteId).orElse(null);
    }


    // Obtener un cliente por su ID
    public ClienteResponseDTO obtenerUsuarioPorId(Integer clienteId) {
        // Usamos Optional para obtener el usuario
        Optional<ClienteEntity> clienteOptional = clienteRepository.findById(clienteId);

        // Si el usuario no está presente, lanzamos una excepción personalizada
        ClienteEntity clienteEntity = clienteOptional.orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Mapear el UsuarioEntity a UsuarioResponseDTO
        return ClienteMapper.toClienteResponseDTO(clienteEntity);
    }



    public Page<ClienteEntity> getAllEnabled(int page,int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest  = PageRequest.of(page, elements,sort);
        return this.clientePagSortRepository.findByclienteDisabledFalse(pageRequest);
    }

/*
si vale
    public Page<ClienteEntity> getAllporEmpresaId(int empresaId,int page,int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest  = PageRequest.of(page, elements,sort);
        return this.clientePagSortRepository.findByEmpresa_EmpresaId(empresaId, pageRequest);
    }

*/

    // Obtener clientes por empresa con paginación y ordenamiento
  /*  public Page<ClienteResponseDTO> getAllPorEmpresaId(int empresaId, int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        Page<ClienteEntity> clientePage = clientePagSortRepository.findByEmpresa_EmpresaId(empresaId, pageRequest);

        List<ClienteResponseDTO> dtoList = clientePage.getContent().stream()
                .map(ClienteMapper::toClienteResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageRequest, clientePage.getTotalElements());
    }
*/
  /*  public Page<ClienteResponseDTO> getAllPorEmpresaId(int empresaId, int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        Page<ClienteEntity> clientePage = clientePagSortRepository.findByEmpresa_EmpresaId(empresaId, pageRequest);

        List<ClienteResponseDTO> dtoList = clientePage.getContent().stream()
                .map(ClienteMapper::toClienteResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageRequest, clientePage.getTotalElements());  // ← Devuelve un Page con DTOs
    }*/


    public Page<ClienteResponseDTO> getAllPorEmpresaId(int empresaId, Pageable pageable) {
        // Obtener la página de entidades desde la base de datos
        Page<ClienteEntity> clientePage = clientePagSortRepository.findByEmpresa_EmpresaId(empresaId, pageable);

        // Convertir las entidades a DTOs usando ClienteMapper
        List<ClienteResponseDTO> clienteDTOList = clientePage.getContent().stream()
                .map(ClienteMapper::toClienteResponseDTO)
                .collect(Collectors.toList());

        // Retornar la página transformada con los DTOs
        return new PageImpl<>(clienteDTOList, pageable, clientePage.getTotalElements());
    }


    // Método auxiliar para verificar si existe un cliente por RUC y crear si no existe
    public ClienteResponseDTO verificarYCrearClientePorRuc(ClienteRequestDTO clienteRequestDTO) {
        // Verificar si ya existe un cliente con el mismo RUC
        Optional<ClienteEntity> clienteExistente = clienteRepository
                .findByClienteIdentificacion(clienteRequestDTO.getClienteIdentificacion());

        if (clienteExistente.isPresent()) {
            // Si ya existe, retornar el DTO del cliente existente
            return ClienteMapper.toClienteResponseDTO(clienteExistente.get());
        }

        // Si no existe, registrar el cliente usando el método crearCliente
        return crearCliente(clienteRequestDTO);
    }


    // Método para crear un nuevo usuario
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequestDTO) {
         System.out.println(clienteRequestDTO.getClienteCorreo());

        // Verificar si ya existe un cliente con el mismo RUC
        Optional<ClienteEntity> clienteExistente = clienteRepository
                .findByClienteIdentificacion(clienteRequestDTO.getClienteIdentificacion());

        if (clienteExistente.isPresent()) {
            // Lanzar excepción si el cliente ya existe
            throw new ClienteAlreadyExistsException(
                    "El cliente con RUC " + clienteRequestDTO.getClienteIdentificacion() + " ya existe"
            );
        }



        // Verificar si ya existe un cliente con el mismo correo
        Optional<ClienteEntity> clienteExistentePorCorreo = clienteRepository
                .findByClienteCorreo(clienteRequestDTO.getClienteCorreo());

        if (clienteExistentePorCorreo.isPresent()) {
            throw new ClienteAlreadyExistsException(
                    "El cliente con correo " + clienteRequestDTO.getClienteCorreo() + " ya existe"
            );
        }



        // Si no existe, buscar la empresa por el ID
        EmpresaEntity empresaEntity = empresaRepository.findById(clienteRequestDTO.getEmpresaId())
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));

        // Convertir el DTO a la entidad, y asignar la empresa
        ClienteEntity clienteEntity = ClienteMapper.toClienteEntity(clienteRequestDTO);
        clienteEntity.setEmpresa(empresaEntity);  // Establecer la relación de la empresa al cliente

        // Guardar el cliente en la base de datos
        ClienteEntity savedClienteEntity = clienteRepository.save(clienteEntity);

        // Mapear la entidad guardada a DTO de respuesta
        return ClienteMapper.toClienteResponseDTO(savedClienteEntity);
    }



    public ClienteResponseDTO actualizarCliente(Integer clienteId, ClienteRequestDTO clienteRequestDTO) {
        // Buscar el cliente en la base de datos
        ClienteEntity clienteEntity = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Si la empresa cambia, buscar la nueva empresa
        if (clienteRequestDTO.getEmpresaId() != null) {
            EmpresaEntity empresaEntity = empresaRepository.findById(clienteRequestDTO.getEmpresaId())
                    .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));
            clienteEntity.setEmpresa(empresaEntity);
        }


        // Actualizar los datos del cliente si vienen en la petición
        if (clienteRequestDTO.getClienteNombre() != null) {
            clienteEntity.setClienteNombre(clienteRequestDTO.getClienteNombre());
        }
        if (clienteRequestDTO.getClienteIdentificacion() != null) {
            clienteEntity.setClienteIdentificacion(clienteRequestDTO.getClienteIdentificacion());
        }
        if (clienteRequestDTO.getClienteTelefono() != null) {
            clienteEntity.setClienteTelefono(clienteRequestDTO.getClienteTelefono());
        }

        if (clienteRequestDTO.getClienteDireccion() != null) {
            clienteEntity.setClienteDireccion(clienteRequestDTO.getClienteDireccion());
        }
        if (clienteRequestDTO.getClienteDisabled() != null) {
            clienteEntity.setClienteDisabled(clienteRequestDTO.getClienteDisabled());
        }



        // Guardar los cambios en la base de datos
        ClienteEntity updatedClienteEntity = clienteRepository.save(clienteEntity);

        // Mapear la entidad actualizada a DTO de respuesta y retornarla
        return ClienteMapper.toClienteResponseDTO(updatedClienteEntity);
    }



    public void deleteCliente(Integer clienteId) {
        // Buscar el usuario en la base de datos
        ClienteEntity clienteEntity = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        // Eliminar el usuario
        clienteRepository.delete(clienteEntity);
    }


    public boolean exists(int clienteId) {
        return this.clienteRepository.existsById(clienteId);
    }


    public ClienteResponseDTO getClienteById(Integer clienteId) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> {
                    ClienteResponseDTO dto = new ClienteResponseDTO();
                    dto.setClienteId(cliente.getClienteId());
                    dto.setClienteNombre(cliente.getClienteNombre());
                    dto.setClienteCorreo(cliente.getClienteCorreo());
                    dto.setClienteTelefono(cliente.getClienteTelefono());
                    dto.setClienteDireccion(cliente.getClienteDireccion());
                    // setea otros campos necesarios
                    return dto;
                })
                .orElse(null); // o lanza una excepción si prefieres
    }

}
