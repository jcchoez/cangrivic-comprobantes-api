package com.facturas.cangrivic.service;

import com.facturas.cangrivic.dto.batch.factura.FacturaDTO;
import com.facturas.cangrivic.dto.batch.factura.FacturaDetalleDTO;
import com.facturas.cangrivic.dto.batch.factura.LoteFacturasDTO;
import com.facturas.cangrivic.dto.batch.factura.RespuestaLoteFacturasDTO;
import com.facturas.cangrivic.exception.*;
import com.facturas.cangrivic.persistence.entity.*;
import com.facturas.cangrivic.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final FacturaDetalleRepository facturaDetalleRepository;
    //private final RabbitTemplate rabbitTemplate;


    public void procesarLote(List<FacturaDTO> facturas, String loteId) {
            for (FacturaDTO facturaDTO : facturas) {
                if (facturaRepository.existsByFacturaNumero(facturaDTO.getFacturaNumero())) {
                    throw new RuntimeException("Factura " + facturaDTO.getFacturaNumero() + "ya existe");
                }




                EmpresaEntity empresa = empresaRepository.findById(facturaDTO.getEmpresaId())
                        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

                UsuarioEntity usuario = usuarioRepository.findById(facturaDTO.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                ClienteEntity cliente = clienteRepository.findById(facturaDTO.getClienteId())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));



                FacturaEntity factura = new FacturaEntity();
                factura.setFacturaNumero(facturaDTO.getFacturaNumero());
                factura.setFacturaClaveAcceso(facturaDTO.getFacturaClaveAcceso());
                factura.setFacturaTotal(facturaDTO.getFacturaTotal());
                factura.setFacturaFecha(facturaDTO.getFacturaFecha());
                factura.setEmpresa(empresa);
                factura.setUsuario(usuario);
                factura.setCliente(cliente);
                factura.setFacturaEstado(FacturaEntity.EstadoFactura.PENDIENTE);

                facturaRepository.save(factura);

                for (FacturaDetalleDTO detalleDTO : facturaDTO.getDetalles()) {

                    ProductoEntity producto = productoRepository.findById(detalleDTO.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


                    FacturaDetalleEntity detalle = new FacturaDetalleEntity();
                    detalle.setFactura(factura);
                    detalle.setProducto(producto);
                    detalle.setDetalleCantidad(detalleDTO.getCantidad());
                    detalle.setDetallePrecioUnitario(detalleDTO.getPrecioUnitario());
                    facturaDetalleRepository.save(detalle);
                }

                // Enviamos a la cola de RabbitMQ para procesamiento asincrónico
                //rabbitTemplate.convertAndSend("facturasQueue", facturaDTO);
            }

    }












    public RespuestaLoteFacturasDTO procesarLote2(List<FacturaDTO> facturas, String loteId) {
        List<String> facturasProcesadas = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        for (FacturaDTO facturaDTO : facturas) {
            try {
                if (facturaRepository.existsByFacturaNumero(facturaDTO.getFacturaNumero())) {
                    errores.add("Factura " + facturaDTO.getFacturaNumero() + " ya existe.");
                    continue;  // Saltamos esta factura y seguimos con la siguiente
                }

                EmpresaEntity empresa = empresaRepository.findById(facturaDTO.getEmpresaId())
                        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

                UsuarioEntity usuario = usuarioRepository.findById(facturaDTO.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                ClienteEntity cliente = clienteRepository.findById(facturaDTO.getClienteId())
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

                FacturaEntity factura = new FacturaEntity();
                factura.setFacturaNumero(facturaDTO.getFacturaNumero());
                factura.setFacturaClaveAcceso(facturaDTO.getFacturaClaveAcceso());
                factura.setFacturaTotal(facturaDTO.getFacturaTotal());
                factura.setFacturaFecha(facturaDTO.getFacturaFecha());
                factura.setEmpresa(empresa);
                factura.setUsuario(usuario);
                factura.setCliente(cliente);
                factura.setFacturaEstado(FacturaEntity.EstadoFactura.PENDIENTE);

                facturaRepository.save(factura);

                for (FacturaDetalleDTO detalleDTO : facturaDTO.getDetalles()) {
                    ProductoEntity producto = productoRepository.findById(detalleDTO.getProductoId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    FacturaDetalleEntity detalle = new FacturaDetalleEntity();
                    detalle.setFactura(factura);
                    detalle.setProducto(producto);
                    detalle.setDetalleCantidad(detalleDTO.getCantidad());
                    detalle.setDetallePrecioUnitario(detalleDTO.getPrecioUnitario());
                    facturaDetalleRepository.save(detalle);
                }

                // Enviamos a la cola de RabbitMQ para procesamiento asincrónico
               // rabbitTemplate.convertAndSend("facturasQueue", facturaDTO);

                // Agregamos la factura procesada correctamente
                facturasProcesadas.add("Factura " + facturaDTO.getFacturaNumero() + " procesada con éxito.");

            } catch (Exception e) {
                // Capturamos errores sin detener el proceso
                errores.add("Error procesando factura " + facturaDTO.getFacturaNumero() + ": " + e.getMessage());
            }
        }

        // Retornamos el resumen del proceso
        return new RespuestaLoteFacturasDTO(facturasProcesadas, errores);
    }


    public RespuestaLoteFacturasDTO procesarLote3(List<FacturaDTO> facturas, String loteId) {
        List<String> facturasProcesadas = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        for (FacturaDTO facturaDTO : facturas) {
            try {
                if (facturaRepository.existsByFacturaNumero(facturaDTO.getFacturaNumero())) {
                    errores.add("Factura " + facturaDTO.getFacturaNumero() + " ya existe.");
                    continue; // Pasa a la siguiente factura
                }

                EmpresaEntity empresa = empresaRepository.findById(facturaDTO.getEmpresaId())
                        .orElseThrow(() -> new EmpresaNotFoundException("Empresa con ID " + facturaDTO.getEmpresaId() + " no encontrada"));

                UsuarioEntity usuario = usuarioRepository.findById(facturaDTO.getUsuarioId())
                        .orElseThrow(() -> new UsuarioNotFoundException("Usuario con ID " + facturaDTO.getUsuarioId() + " no encontrado"));

                ClienteEntity cliente = clienteRepository.findById(facturaDTO.getClienteId())
                        .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID " + facturaDTO.getClienteId() + " no encontrado"));

                // Obtener IDs de productos de la factura
                List<Integer> productoIds = facturaDTO.getDetalles().stream()
                        .map(FacturaDetalleDTO::getProductoId)
                        .collect(Collectors.toList());

                // Validar si todos los productos existen en una sola consulta
                List<Integer> productosExistentes = productoRepository.findAllById(productoIds)
                        .stream().map(ProductoEntity::getProductoId).collect(Collectors.toList());

                if (productosExistentes.size() != productoIds.size()) {
                    errores.add("Factura " + facturaDTO.getFacturaNumero() + " no procesada. Uno o más productos no existen.");
                    continue; // No se guarda esta factura y sigue con la siguiente
                }

                // Crear y guardar la factura
                FacturaEntity factura = new FacturaEntity();
                factura.setFacturaNumero(facturaDTO.getFacturaNumero());
                factura.setFacturaClaveAcceso(facturaDTO.getFacturaClaveAcceso());
                factura.setFacturaTotal(facturaDTO.getFacturaTotal());
                factura.setFacturaFecha(facturaDTO.getFacturaFecha());
                factura.setEmpresa(empresa);
                factura.setUsuario(usuario);
                factura.setCliente(cliente);
                factura.setFacturaEstado(FacturaEntity.EstadoFactura.PENDIENTE);

                facturaRepository.save(factura);

                // Guardar detalles de la factura
                for (FacturaDetalleDTO detalleDTO : facturaDTO.getDetalles()) {
                    ProductoEntity producto = productoRepository.findById(detalleDTO.getProductoId())
                            .orElseThrow(() -> new ProductoNotFoundException("Producto con ID " + detalleDTO.getProductoId() + " no encontrado")); // No debería pasar

                    FacturaDetalleEntity detalle = new FacturaDetalleEntity();
                    detalle.setFactura(factura);
                    detalle.setProducto(producto);
                    detalle.setDetalleCantidad(detalleDTO.getCantidad());
                    detalle.setDetallePrecioUnitario(detalleDTO.getPrecioUnitario());

                    facturaDetalleRepository.save(detalle);
                }

                facturasProcesadas.add("Factura " + facturaDTO.getFacturaNumero() + "procesada con éxito.");

                //rabbitTemplate.convertAndSend("facturasQueue", facturaDTO); //SRI POR COLA -> HACE 1 --> OTRO EN 1 EN 1

            }  catch (FacturaDuplicadaException | ProductoNoExistenteException | ClienteNotFoundException | EmpresaNotFoundException e) {
                // Captura las excepciones específicas y muestra un mensaje controlado
                errores.add("Error en el procesamiento de la factura " + facturaDTO.getFacturaNumero() + ": " + e.getMessage());
            } catch (DataIntegrityViolationException e) {
                // Captura violaciones de integridad de datos (como claves duplicadas o restricciones de integridad) y proporciona un mensaje amigable
                errores.add("Error en la base de datos al procesar la factura " + facturaDTO.getFacturaNumero() + ": No se puede completar la operación debido a una restricción de datos.");
            } catch (Exception e) {
                // Captura excepciones genéricas y agrega un mensaje controlado
                errores.add("Error procesando factura " + facturaDTO.getFacturaNumero() + ": Ha ocurrido un error inesperado.");
            }
        }

        return new RespuestaLoteFacturasDTO(facturasProcesadas, errores);
    }


}
