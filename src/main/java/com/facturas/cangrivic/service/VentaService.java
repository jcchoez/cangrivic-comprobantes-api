package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.cliente.ClienteResponseDTO;
import com.facturas.cangrivic.dto.venta.VentaConClienteDTO;
import com.facturas.cangrivic.dto.venta.VentaItemResponseDTO;
import com.facturas.cangrivic.dto.venta.VentaRequestDTO;
import com.facturas.cangrivic.dto.venta.VentaResponseDTO;
import com.facturas.cangrivic.exception.*;
import com.facturas.cangrivic.persistence.entity.*;
import com.facturas.cangrivic.persistence.repository.ClienteRepository;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import com.facturas.cangrivic.persistence.repository.ProductoRepository;
import com.facturas.cangrivic.persistence.repository.VentaRepository;
import com.facturas.cangrivic.service.mapper.VentaConClienteMapper;
import com.facturas.cangrivic.service.mapper.VentaItemMapper;
import com.facturas.cangrivic.service.mapper.VentaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    private final ProductoRepository productoRepository;

    private final ClienteRepository clienteRepository;

    private final SecuencialService secuencialService;

    private final EmpresaRepository empresaRepository;


    @Autowired // Añade esta anotación para inyectar el mapper
    private VentaConClienteMapper ventaConClienteMapper;



    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository, SecuencialService secuencialService, EmpresaRepository empresaRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.secuencialService = secuencialService;
        this.empresaRepository = empresaRepository;
    }

    // Obtener todas las ventas
    public List<VentaResponseDTO> obtenerTodasLasVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(VentaMapper::toVentaResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener venta por ID
    public VentaResponseDTO obtenerVentaPorId(Long ventaId) {
        VentaEntity ventaEntity = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada con ID: " + ventaId));
        return VentaMapper.toVentaResponseDTO(ventaEntity);
    }



// Crear una nueva venta con items
public VentaResponseDTO crearVenta(VentaRequestDTO ventaRequestDTO) {

    //busca la empresa por el ID
    ClienteEntity clienteEntity = clienteRepository.findById(ventaRequestDTO.getClienteId())
            .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

    // 2. Obtener el ID de la empresa del DTO
    EmpresaEntity empresaEntity = empresaRepository.findById(ventaRequestDTO.getEmpresaId())
            .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrado"));

    // 3. ¡GENERAR EL NÚMERO DE COMPROBANTE!
    // Llamas al servicio que se encarga de la lógica de secuenciales
    long numeroComprobante = secuencialService.generarNumeroComprobante(empresaEntity.getEmpresaId(), 'f');

    long codigoNumerico = secuencialService.generarNumeroComprobante(empresaEntity.getEmpresaId(), 'c');


    //f = secuencial de factura
    //c = codigo numerico

    // 1. Convertir venta principal
    VentaEntity ventaEntity = VentaMapper.toVentaEntity(ventaRequestDTO);

    // 5. Asignar el número secuencial y el ID de la empresa
    ventaEntity.setSecuencial(String.valueOf(numeroComprobante));
    ventaEntity.setCodigoNumerico(String.valueOf(codigoNumerico));
    ventaEntity.setEmpresaId(empresaEntity.getEmpresaId());

    // 2. Procesar items
    if (ventaRequestDTO.getItems() != null) {
        List<VentaItemEntity> items = ventaRequestDTO.getItems()
                .stream()
                .map(itemDTO -> {
                    // Buscar producto (esto sí va en el service)
                    ProductoEntity producto = productoRepository.findById(itemDTO.getProductoId())
                            .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

                    // Convertir item (mapper solo para datos)
                    VentaItemEntity item = VentaItemMapper.toVentaItemEntity(itemDTO);
                    item.setProducto(producto);
                    item.setVenta(ventaEntity);
                    return item;
                })
                .collect(Collectors.toList());

        ventaEntity.setItems(items);
    }

    // 3. Guardar
    VentaEntity savedVenta = ventaRepository.save(ventaEntity);
    return VentaMapper.toVentaResponseDTO(savedVenta);
}




    // Actualizar una venta
    public VentaResponseDTO actualizarVenta(Long ventaId, VentaRequestDTO ventaRequestDTO) {
        VentaEntity ventaEntity = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada con ID: " + ventaId));

        if (ventaRequestDTO.getClienteId() != null) {
            ventaEntity.setClienteId(ventaRequestDTO.getClienteId());
        }

        if (ventaRequestDTO.getFechaVenta() != null) {
            ventaEntity.setFechaVenta(ventaRequestDTO.getFechaVenta());
        }

        if (ventaRequestDTO.getTotal() != null) {
            ventaEntity.setTotal(ventaRequestDTO.getTotal());
        }

        VentaEntity updatedVenta = ventaRepository.save(ventaEntity);
        return VentaMapper.toVentaResponseDTO(updatedVenta);
    }

    // Eliminar venta
    public void eliminarVenta(Long ventaId) {
        VentaEntity ventaEntity = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada con ID: " + ventaId));
        ventaRepository.delete(ventaEntity);
    }






    public Page<VentaConClienteDTO> getVentasPorRangoPaginado(Integer empresaId, LocalDate fechaDesde, LocalDate fechaHasta, Pageable pageable) {
        LocalDateTime inicio = fechaDesde.atStartOfDay();
        LocalDateTime fin = fechaHasta.plusDays(1).atStartOfDay();

        Page<VentaEntity> ventasPage = ventaRepository
                .findByEmpresaIdAndFechaVentaGreaterThanEqualAndFechaVentaLessThan(empresaId, inicio, fin, pageable);

        List<VentaConClienteDTO> ventasDTO = ventasPage.getContent().stream()
                .map(ventaEntity -> ventaConClienteMapper.toVentaConClienteDTO(ventaEntity))
                .collect(Collectors.toList());

        return new PageImpl<>(ventasDTO, pageable, ventasPage.getTotalElements());
    }

}
