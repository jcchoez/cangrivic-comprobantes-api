package com.facturas.cangrivic.service.mapper;

import com.facturas.cangrivic.dto.cliente.ClienteDetalleDTO;
import com.facturas.cangrivic.dto.venta.VentaConClienteDTO;
import com.facturas.cangrivic.dto.venta.VentaItemResponseDTO;
import com.facturas.cangrivic.persistence.entity.ClienteEntity;
import com.facturas.cangrivic.persistence.entity.VentaEntity;
import com.facturas.cangrivic.persistence.repository.ClienteRepository;
import com.facturas.cangrivic.persistence.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // Asegúrate de tener esta anotación
public class VentaConClienteMapper {

    @Autowired
    private ClienteRepository clienteRepository;

    private final ProductoRepository productoRepository;

    public VentaConClienteMapper(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }


    public VentaConClienteDTO toVentaConClienteDTO(VentaEntity ventaEntity) {
        VentaConClienteDTO dto = new VentaConClienteDTO();
        dto.setVentaId(ventaEntity.getVentaId());
        dto.setFechaVenta(ventaEntity.getFechaVenta());
        dto.setEmpresaId(ventaEntity.getEmpresaId());
        dto.setTotal(ventaEntity.getTotal());


        System.out.println("ISDDDDD: "+ventaEntity.getClienteId());
        // Obtener datos completos del cliente
        if (ventaEntity.getClienteId() != null) {
            ClienteEntity cliente = clienteRepository.findById(ventaEntity.getClienteId()).orElse(null);
            if (cliente != null) {
                ClienteDetalleDTO clienteDTO = new ClienteDetalleDTO();
                clienteDTO.setClienteId(cliente.getClienteId());
                clienteDTO.setNombre(cliente.getClienteNombre());
                clienteDTO.setEmail(cliente.getClienteCorreo());
                clienteDTO.setTelefono(cliente.getClienteTelefono());
                clienteDTO.setDireccion(cliente.getClienteDireccion());
                dto.setCliente(clienteDTO);
            }
        }

        // Mapear items si existen
        if (ventaEntity.getItems() != null) {
            List<VentaItemResponseDTO> itemsDTO = ventaEntity.getItems()
                    .stream()
                    .map(item -> {
                        VentaItemResponseDTO itemDTO = new VentaItemResponseDTO();
                        itemDTO.setProductoId(item.getProducto().getProductoId());
                        itemDTO.setCantidad(item.getCantidad());
                        itemDTO.setPrecioUnitario(item.getPrecioUnitario());
                        itemDTO.setSubtotal(item.getSubtotal());

                        // Consultar producto
                        productoRepository.findById(item.getProducto().getProductoId()).ifPresent(producto -> {
                            itemDTO.setCodigo(producto.getProductoCodigo());
                            itemDTO.setNombre(producto.getProductoNombre());
                        });

                        return itemDTO;
                    })
                    .collect(Collectors.toList());
            dto.setItems(itemsDTO);
        }

        return dto;
    }
}