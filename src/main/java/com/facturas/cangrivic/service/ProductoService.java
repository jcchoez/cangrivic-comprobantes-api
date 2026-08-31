package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.cliente.ClienteResponseDTO;
import com.facturas.cangrivic.dto.producto.ProductoRequestDTO;
import com.facturas.cangrivic.dto.producto.ProductoResponseDTO;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.ProductoNotFoundException;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.EmpresaEntity;
import com.facturas.cangrivic.persistence.entity.ProductoEntity;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import com.facturas.cangrivic.persistence.repository.ProductoPagSortRepository;
import com.facturas.cangrivic.persistence.repository.ProductoRepository;
import com.facturas.cangrivic.service.mapper.ClienteMapper;
import com.facturas.cangrivic.service.mapper.ProductoMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final EmpresaRepository empresaRepository;
    private final ProductoPagSortRepository productoPagSortRepository;

    public ProductoService(ProductoRepository productoRepository, EmpresaRepository empresaRepository, ProductoPagSortRepository productoPagSortRepository) {
        this.productoRepository = productoRepository;
        this.empresaRepository = empresaRepository;
        this.productoPagSortRepository = productoPagSortRepository;
    }


    public Page<ProductoEntity> getAllEnabled(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest  = PageRequest.of(page, elements,sort);
        return this.productoPagSortRepository.findByproductoDisabledFalse(pageRequest);
    }


    public Page<ProductoResponseDTO> getAllPorEmpresaId(int empresaId, Pageable pageable) {
        // Obtener la página de entidades desde la base de datos
        Page<ProductoEntity> productoPage = productoPagSortRepository.findByEmpresa_EmpresaId(empresaId, pageable);

        // Convertir las entidades a DTOs usando ClienteMapper
        List<ProductoResponseDTO> productoDTOList = productoPage.getContent().stream()
                .map(ProductoMapper::toProductoResponseDTO)
                .collect(Collectors.toList());

        // Retornar la página transformada con los DTOs
        return new PageImpl<>(productoDTOList, pageable, productoPage.getTotalElements());
    }


    //Obtener Producto por Id
    public ProductoResponseDTO obtenerProductoPorId(Integer productoId) {
        // Usamos Optional para obtener el producto
        Optional<ProductoEntity> productoOptional = productoRepository.findById(productoId);

        // si el usuario no esta presente, lanzamos una excepcion personalizada
        ProductoEntity productoEntity = productoOptional.orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        // Mapper el ProductoEntity a ProductoResponseDTO
        return ProductoMapper.toProductoResponseDTO(productoEntity);
    }

    public ProductoResponseDTO crearProducto(ProductoRequestDTO productoRequestDTO) {
        //busca la empresa por el ID
        EmpresaEntity empresaEntity = empresaRepository.findById(productoRequestDTO.getEmpresaId())
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));

        //Convertir el DTO  al producto y asignar la empresa
        ProductoEntity productoEntity = ProductoMapper.toProductoEntity(productoRequestDTO);
        productoEntity.setEmpresa(empresaEntity);

        //Guardar el producto en la base de datos
        ProductoEntity savedProductoEntity = productoRepository.save(productoEntity);

        //Mapper el producto guardado a DTO de respuesta
        return ProductoMapper.toProductoResponseDTO(savedProductoEntity);

    }


    public ProductoResponseDTO actualizarProducto (Integer productoId, ProductoRequestDTO productoRequestDTO) {
        // Buscar el producto en la base de datos
        ProductoEntity productoEntity = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

        //si la empresa cambia, buscar la nueva empresa
        if (productoRequestDTO.getEmpresaId() != null) {
            EmpresaEntity empresaEntity = empresaRepository.findById(productoRequestDTO.getEmpresaId())
                    .orElseThrow(()-> new EmpresaNotFoundException("Empresa no encontrada"));
            productoEntity.setEmpresa(empresaEntity);
        }

        //actualiza los datos del producto is vienen en la peticion
        if (productoRequestDTO.getProductoNombre() != null ){
            productoEntity.setProductoNombre(productoRequestDTO.getProductoNombre());
        }

        if (productoRequestDTO.getProductoCodigo() != null ){
            productoEntity.setProductoCodigo(productoRequestDTO.getProductoCodigo());
        }

        if (productoRequestDTO.getProductoDescripcion() != null ){
            productoEntity.setProductoDescripcion(productoRequestDTO.getProductoDescripcion());
        }

        if (productoRequestDTO.getProductoPrecio() != null ){
            productoEntity.setProductoPrecio(productoRequestDTO.getProductoPrecio());
        }

        if (productoRequestDTO.getProductoStock() != null ){
            productoEntity.setProductoStock(productoRequestDTO.getProductoStock());
        }

        if (productoRequestDTO.getProductoDisabled() != null ){
            productoEntity.setProductoDisabled(productoRequestDTO.getProductoDisabled());
        }

        if (productoRequestDTO.getProductoEstado() != null ){
            productoEntity.setProductoEstado(productoRequestDTO.getProductoEstado());
        }

        ProductoEntity updateProductoEntity = productoRepository.save(productoEntity);

        return ProductoMapper.toProductoResponseDTO(updateProductoEntity);

    }

    public void deleteProducto(Integer productoId) {
        // Busacr el producto de la base de datos
        ProductoEntity productoEntity = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNotFoundException("El producto no existe"));

        //Eliminar el producto
        productoRepository.delete(productoEntity);
    }

}
