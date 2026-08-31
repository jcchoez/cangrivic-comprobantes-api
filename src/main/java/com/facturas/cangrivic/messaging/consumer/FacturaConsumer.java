package com.facturas.cangrivic.messaging.consumer;
import com.facturas.cangrivic.dto.batch.factura.FacturaDTO;
import com.facturas.cangrivic.persistence.entity.FacturaEntity;
import com.facturas.cangrivic.persistence.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacturaConsumer {
    private final FacturaRepository facturaRepository;

    //@RabbitListener(queues = "facturasQueue")
    public void procesarFactura(FacturaDTO facturaDTO) {
        FacturaEntity factura = facturaRepository.findByFacturaNumero(facturaDTO.getFacturaNumero())
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // En este punto, facturaDTO ya está deserializado y disponible para su uso.
        System.out.println("Procesando Rabbit factura: " + facturaDTO.getFacturaNumero());

        try {
            Thread.sleep(3000); // Simulación de procesamiento
            System.out.println("RabbitMQ procesando factura: " + facturaDTO.getFacturaNumero());
            factura.setFacturaEstado(FacturaEntity.EstadoFactura.AUTORIZADA);
        } catch (Exception e) {
            factura.setFacturaEstado(FacturaEntity.EstadoFactura.RECHAZADA);
        }

        facturaRepository.save(factura);
    }
}