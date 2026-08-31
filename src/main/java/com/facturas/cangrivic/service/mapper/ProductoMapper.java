package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.cliente.ClienteRequestDTO;
import com.facturas.cangrivic.dto.producto.ProductoRequestDTO;
import com.facturas.cangrivic.dto.producto.ProductoResponseDTO;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.ProductoEntity;

public class ProductoMapper {
    // Método para mapear de ProductoEntity a ProductoResponseDTO
    public static ProductoResponseDTO toProductoResponseDTO(ProductoEntity productoEntity) {
        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO();
        productoResponseDTO.setProductoId(productoEntity.getProductoId());
        productoResponseDTO.setProductoNombre(productoEntity.getProductoNombre());
        productoResponseDTO.setProductoCodigo(productoEntity.getProductoCodigo());
        productoResponseDTO.setProductoDescripcion(productoEntity.getProductoDescripcion());
        productoResponseDTO.setProductoPrecio(productoEntity.getProductoPrecio());
        productoResponseDTO.setProductoStock(productoEntity.getProductoStock());
        productoResponseDTO.setProductoDisabled(productoEntity.getProductoDisabled());
        productoResponseDTO.setProductoEstado(productoEntity.getProductoEstado());
        productoResponseDTO.setEmpresaId(productoEntity.getEmpresa().getEmpresaId());
        return productoResponseDTO;
    }

    // Método para mapear de ProductoRequestDTO a ProductoEntity (para guardar el producto)
    public static ProductoEntity toProductoEntity(ProductoRequestDTO productoRequestDTO) {
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setProductoId(productoRequestDTO.getProductoId());
        productoEntity.setProductoNombre(productoRequestDTO.getProductoNombre());
        productoEntity.setProductoCodigo(productoRequestDTO.getProductoCodigo());
        productoEntity.setProductoDescripcion(productoRequestDTO.getProductoDescripcion());
        productoEntity.setProductoPrecio(productoRequestDTO.getProductoPrecio());
        productoEntity.setProductoStock(productoRequestDTO.getProductoStock());
        productoEntity.setProductoDisabled(productoRequestDTO.getProductoDisabled());
        productoEntity.setProductoEstado(productoRequestDTO.getProductoEstado());
        return productoEntity;
    }
}
