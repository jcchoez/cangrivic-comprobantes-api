package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.compra.CompraConProveedorDTO;
import com.facturas.cangrivic.dto.compra.CompraItemResponseDTO;
import com.facturas.cangrivic.dto.proveedor.ProveedorDetalleDTO;
import com.facturas.cangrivic.persistence.entity.CompraEntity;
import com.facturas.cangrivic.persistence.entity.ProveedorEntity;
import com.facturas.cangrivic.persistence.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraConProveedorMapper {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public CompraConProveedorDTO toCompraConProveedorDTO(CompraEntity entity) {
        if (entity == null) {
            return null;
        }

        CompraConProveedorDTO dto = new CompraConProveedorDTO();
        dto.setCompraId(entity.getCompraId());
        dto.setFechaCompra(entity.getFechaCompra());
        dto.setEmpresaId(entity.getEmpresaId());
        dto.setTotal(entity.getTotal());

        // Cargar proveedor
        if (entity.getProveedorId() != null) {
            ProveedorEntity proveedor = proveedorRepository.findById(entity.getProveedorId()).orElse(null);
            if (proveedor != null) {
                ProveedorDetalleDTO proveedorDTO = new ProveedorDetalleDTO();
                proveedorDTO.setProveedorId(proveedor.getProveedorId());
                // Usamos los campos que existen en tu ProveedorDetalleDTO
                proveedorDTO.setNombre(proveedor.getProveedorNombre()); // campo en tu entidad
                proveedorDTO.setEmail(proveedor.getProveedorCorreo());   // campo en tu entidad
                proveedorDTO.setTelefono(proveedor.getProveedorTelefono());
                proveedorDTO.setDireccion(proveedor.getProveedorDireccion());
                // Si tuvieras identificacion, podrías agregarlo, pero no está en el DTO
                dto.setProveedor(proveedorDTO);
            }
        }

        // Mapear items
        if (entity.getItems() != null) {
            List<CompraItemResponseDTO> itemsDTO = entity.getItems()
                    .stream()
                    .map(item -> {
                        CompraItemResponseDTO itemDTO = new CompraItemResponseDTO();
                        itemDTO.setProductoId(item.getProducto().getProductoId());
                        itemDTO.setCantidad(item.getCantidad());
                        itemDTO.setPrecioUnitario(item.getPrecioUnitario());
                        itemDTO.setSubtotal(item.getSubtotal());
                        if (item.getProducto() != null) {
                            itemDTO.setNombre(item.getProducto().getProductoNombre());
                            itemDTO.setCodigo(item.getProducto().getProductoCodigo());
                        }
                        return itemDTO;
                    })
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }

        return dto;
    }
}