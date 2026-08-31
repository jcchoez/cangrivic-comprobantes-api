package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.batch.factura.FacturaDTO;
import com.facturas.cangrivic.dto.batch.factura.LoteFacturasDTO;
import com.facturas.cangrivic.dto.batch.factura.RespuestaLoteFacturasDTO;
import com.facturas.cangrivic.persistence.entity.FacturaEntity;
import com.facturas.cangrivic.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factura")
@RequiredArgsConstructor
public class FacturaController {
    private final FacturaService facturaService;
/*

    @PostMapping("/guardar-lote")
    public ResponseEntity<String> guardarLote(@RequestBody LoteFacturasDTO loteDTO) {
        facturaService.procesarLote(loteDTO.getFacturas(), loteDTO.getLoteId());
        return ResponseEntity.ok("Lote " + loteDTO.getLoteId() + " en proceso");
    }
*/


    @PostMapping("/procesar-lote")
    public ResponseEntity<RespuestaLoteFacturasDTO> procesarLote(@RequestBody List<FacturaDTO> facturas,
                                                                 @RequestParam String loteId) {
        RespuestaLoteFacturasDTO respuesta = facturaService.procesarLote3(facturas, loteId);
        return ResponseEntity.ok(respuesta);
    }
}
