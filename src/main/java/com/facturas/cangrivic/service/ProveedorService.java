package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.proveedor.ProveedorRequestDTO;
import com.facturas.cangrivic.dto.proveedor.ProveedorResponseDTO;
import com.facturas.cangrivic.exception.*;
import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import com.facturas.cangrivic.persistence.entity.ProveedorEntity;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import com.facturas.cangrivic.persistence.repository.ProveedorPagSortRepository;
import com.facturas.cangrivic.persistence.repository.ProveedorRepository;
import com.facturas.cangrivic.service.mapper.ProveedorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorPagSortRepository proveedorPagSortRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository,
                            ProveedorPagSortRepository proveedorPagSortRepository,
                            EmpresaRepository empresaRepository) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorPagSortRepository = proveedorPagSortRepository;
        this.empresaRepository = empresaRepository;
    }

    public ProveedorEntity get(int proveedorId) {
        return proveedorRepository.findById(proveedorId).orElse(null);
    }

    public ProveedorResponseDTO obtenerProveedorPorId(Integer proveedorId) {
        ProveedorEntity entity = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));
        return ProveedorMapper.toProveedorResponseDTO(entity);
    }

    public Page<ProveedorEntity> getAllEnabled(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.proveedorPagSortRepository.findByproveedorDisabledFalse(pageRequest);
    }

    public Page<ProveedorResponseDTO> getAllPorEmpresaId(int empresaId, Pageable pageable) {
        Page<ProveedorEntity> entityPage = proveedorPagSortRepository.findByEmpresa_EmpresaId(empresaId, pageable);
        List<ProveedorResponseDTO> dtoList = entityPage.getContent().stream()
                .map(ProveedorMapper::toProveedorResponseDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public ProveedorResponseDTO verificarYCrearProveedorPorIdentificacion(ProveedorRequestDTO requestDTO) {
        Optional<ProveedorEntity> existente = proveedorRepository
                .findByProveedorIdentificacion(requestDTO.getProveedorIdentificacion());
        if (existente.isPresent()) {
            return ProveedorMapper.toProveedorResponseDTO(existente.get());
        }
        return crearProveedor(requestDTO);
    }

    public ProveedorResponseDTO crearProveedor(ProveedorRequestDTO requestDTO) {
        // Validar unicidad de identificación
        Optional<ProveedorEntity> existenteIdent = proveedorRepository
                .findByProveedorIdentificacion(requestDTO.getProveedorIdentificacion());
        if (existenteIdent.isPresent()) {
            throw new ProveedorAlreadyExistsException(
                    "El proveedor con identificación " + requestDTO.getProveedorIdentificacion() + " ya existe"
            );
        }

        // Validar unicidad de correo
        Optional<ProveedorEntity> existenteCorreo = proveedorRepository
                .findByProveedorCorreo(requestDTO.getProveedorCorreo());
        if (existenteCorreo.isPresent()) {
            throw new ProveedorAlreadyExistsException(
                    "El proveedor con correo " + requestDTO.getProveedorCorreo() + " ya existe"
            );
        }

        EmpresaEntity empresa = empresaRepository.findById(requestDTO.getEmpresaId())
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));

        ProveedorEntity entity = ProveedorMapper.toProveedorEntity(requestDTO);
        entity.setEmpresa(empresa);
        ProveedorEntity saved = proveedorRepository.save(entity);
        return ProveedorMapper.toProveedorResponseDTO(saved);
    }

    public ProveedorResponseDTO actualizarProveedor(Integer proveedorId, ProveedorRequestDTO requestDTO) {
        ProveedorEntity entity = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        if (requestDTO.getEmpresaId() != null) {
            EmpresaEntity empresa = empresaRepository.findById(requestDTO.getEmpresaId())
                    .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));
            entity.setEmpresa(empresa);
        }

        if (requestDTO.getProveedorNombre() != null) entity.setProveedorNombre(requestDTO.getProveedorNombre());
        if (requestDTO.getProveedorIdentificacion() != null) entity.setProveedorIdentificacion(requestDTO.getProveedorIdentificacion());
        if (requestDTO.getProveedorTelefono() != null) entity.setProveedorTelefono(requestDTO.getProveedorTelefono());
        if (requestDTO.getProveedorDireccion() != null) entity.setProveedorDireccion(requestDTO.getProveedorDireccion());
        if (requestDTO.getProveedorCorreo() != null) entity.setProveedorCorreo(requestDTO.getProveedorCorreo());
        if (requestDTO.getProveedorDisabled() != null) entity.setProveedorDisabled(requestDTO.getProveedorDisabled());

        ProveedorEntity updated = proveedorRepository.save(entity);
        return ProveedorMapper.toProveedorResponseDTO(updated);
    }

    public void deleteProveedor(Integer proveedorId) {
        ProveedorEntity entity = proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));
        proveedorRepository.delete(entity);
    }

    public boolean exists(int proveedorId) {
        return proveedorRepository.existsById(proveedorId);
    }
}