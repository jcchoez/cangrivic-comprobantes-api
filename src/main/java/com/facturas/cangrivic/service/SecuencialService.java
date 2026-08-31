package com.facturas.cangrivic.service;

import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.SecuencialesNotFoundException;
import com.facturas.cangrivic.persistence.entity.SecuencialEntity;
import com.facturas.cangrivic.persistence.entity.SecuencialLogEntity;
import com.facturas.cangrivic.persistence.repository.SecuencialLogRepository;
import com.facturas.cangrivic.persistence.repository.SecuencialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecuencialService {

    private final SecuencialRepository secuencialRepository;
    private final SecuencialLogRepository secuencialLogRepository;

    public SecuencialService(
            SecuencialRepository secuencialRepository,
            SecuencialLogRepository secuencialLogRepository) {
        this.secuencialRepository = secuencialRepository;
        this.secuencialLogRepository = secuencialLogRepository;
    }

    /**
     * Genera un nuevo número de comprobante para la empresa y tipo especificados.
     * @param empresaId El ID de la empresa.
     * @param tipoSecuencial El nombre del tipo de secuencial (ej. "factura").
     * @return El número de comprobante generado.
     */
    public long generarNumeroComprobante(int empresaId, char tipoSecuencial) {
        // 1. Busca la entidad de catálogo del secuencial
        SecuencialEntity secuencial = secuencialRepository
                .findByNombreAndEmpresaId(tipoSecuencial, empresaId)
                .orElseThrow(() -> new SecuencialesNotFoundException("El secuencial '" + tipoSecuencial + "' no se encontró para la empresa " + empresaId));

        // 2. Crea una nueva entidad de log
        SecuencialLogEntity log = new SecuencialLogEntity();
        log.setSecuencial(secuencial); // JPA maneja la llave foránea automáticamente
        log.setEmpresaId(empresaId);
        log.setFechaCreacion(java.time.LocalDateTime.now());

        // 3. Guarda la entidad del log. El número se generará con AUTO_INCREMENT
        SecuencialLogEntity savedLog = secuencialLogRepository.save(log);

        // 4. Retorna el número generado
        return savedLog.getNumero();
    }
}