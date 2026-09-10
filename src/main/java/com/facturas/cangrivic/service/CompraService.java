package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.compra.CompraConProveedorDTO;
import com.facturas.cangrivic.dto.compra.CompraRequestDTO;
import com.facturas.cangrivic.dto.compra.CompraResponseDTO;
import com.facturas.cangrivic.exception.*;
import com.facturas.cangrivic.persistence.entity.*;
import com.facturas.cangrivic.persistence.repository.CompraRepository;
import com.facturas.cangrivic.persistence.repository.EmpresaRepository;
import com.facturas.cangrivic.persistence.repository.ProductoRepository;
import com.facturas.cangrivic.persistence.repository.ProveedorRepository;
import com.facturas.cangrivic.service.mapper.CompraConProveedorMapper;
import com.facturas.cangrivic.service.mapper.CompraItemMapper;
import com.facturas.cangrivic.service.mapper.CompraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final SecuencialService secuencialService;
    private final EmpresaRepository empresaRepository;

    @Autowired
    private CompraConProveedorMapper compraConProveedorMapper; // Mapper manual inyectado

    public CompraService(CompraRepository compraRepository,
                         ProductoRepository productoRepository,
                         ProveedorRepository proveedorRepository,
                         SecuencialService secuencialService,
                         EmpresaRepository empresaRepository) {
        this.compraRepository = compraRepository;
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.secuencialService = secuencialService;
        this.empresaRepository = empresaRepository;
    }

    public List<CompraResponseDTO> obtenerTodasLasCompras() {
        return compraRepository.findAll()
                .stream()
                .map(CompraMapper::toCompraResponseDTO)
                .collect(Collectors.toList());
    }

    public CompraResponseDTO obtenerCompraPorId(Long compraId) {
        CompraEntity entity = compraRepository.findById(compraId)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada con ID: " + compraId));
        return CompraMapper.toCompraResponseDTO(entity);
    }

    public CompraResponseDTO crearCompra(CompraRequestDTO requestDTO) {
        // Validar proveedor
        ProveedorEntity proveedor = proveedorRepository.findById(requestDTO.getProveedorId())
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        // Validar empresa
        EmpresaEntity empresa = empresaRepository.findById(requestDTO.getEmpresaId())
                .orElseThrow(() -> new EmpresaNotFoundException("Empresa no encontrada"));

        // Generar secuenciales (ajusta el tipo según tu lógica)
        long numeroComprobante = secuencialService.generarNumeroComprobante(empresa.getEmpresaId(), 'f');
        long codigoNumerico = secuencialService.generarNumeroComprobante(empresa.getEmpresaId(), 'c');

        // Convertir y asignar
        CompraEntity entity = CompraMapper.toCompraEntity(requestDTO);
        entity.setSecuencial(String.valueOf(numeroComprobante));
        entity.setCodigoNumerico(String.valueOf(codigoNumerico));
        entity.setEmpresaId(empresa.getEmpresaId());

        // Procesar items
        if (requestDTO.getItems() != null) {
            List<CompraItemEntity> items = requestDTO.getItems()
                    .stream()
                    .map(itemDTO -> {
                        ProductoEntity producto = productoRepository.findById(itemDTO.getProductoId())
                                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));

                        CompraItemEntity item = CompraItemMapper.toCompraItemEntity(itemDTO);
                        item.setProducto(producto);
                        item.setCompra(entity);
                        return item;
                    })
                    .collect(Collectors.toList());
            entity.setItems(items);
        }

        CompraEntity saved = compraRepository.save(entity);
        return CompraMapper.toCompraResponseDTO(saved);
    }

    public CompraResponseDTO actualizarCompra(Long compraId, CompraRequestDTO requestDTO) {
        CompraEntity entity = compraRepository.findById(compraId)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada con ID: " + compraId));

        if (requestDTO.getProveedorId() != null) {
            entity.setProveedorId(requestDTO.getProveedorId());
        }
        if (requestDTO.getFechaCompra() != null) {
            entity.setFechaCompra(requestDTO.getFechaCompra());
        }
        if (requestDTO.getTotal() != null) {
            entity.setTotal(requestDTO.getTotal());
        }
        // La actualización de items no se maneja aquí; podrías implementarla aparte

        CompraEntity updated = compraRepository.save(entity);
        return CompraMapper.toCompraResponseDTO(updated);
    }

    public void eliminarCompra(Long compraId) {
        CompraEntity entity = compraRepository.findById(compraId)
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada con ID: " + compraId));
        compraRepository.delete(entity);
    }

    public Page<CompraConProveedorDTO> getComprasPorRangoPaginado(Integer empresaId,
                                                                  LocalDate fechaDesde,
                                                                  LocalDate fechaHasta,
                                                                  Pageable pageable) {
        LocalDateTime inicio = fechaDesde.atStartOfDay();
        LocalDateTime fin = fechaHasta.plusDays(1).atStartOfDay();

        Page<CompraEntity> comprasPage = compraRepository
                .findByEmpresaIdAndFechaCompraGreaterThanEqualAndFechaCompraLessThan(empresaId, inicio, fin, pageable);

        List<CompraConProveedorDTO> dtoList = comprasPage.getContent().stream()
                .map(compraConProveedorMapper::toCompraConProveedorDTO) // Usa el mapper manual
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, comprasPage.getTotalElements());
    }
}