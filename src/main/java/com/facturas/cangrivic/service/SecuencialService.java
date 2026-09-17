package com.facturas.cangrivic.service;

import com.facturas.cangrivic.exception.SecuencialesNotFoundException;
import com.facturas.cangrivic.persistence.entity.SecuencialEntity;
import com.facturas.cangrivic.persistence.repository.SecuencialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecuencialService {

    private final SecuencialRepository secuencialRepository;

    public SecuencialService(SecuencialRepository secuencialRepository) {
        this.secuencialRepository = secuencialRepository;
    }

    /**
     * Genera un nuevo número de comprobante para la empresa y tipo especificados mediante un UPDATE seguro.
     * @param empresaId El ID de la empresa.
     * @param tipoSecuencial El carácter del tipo de secuencial (ej. 'f' para factura, 'c' para código numérico).
     * @return El número de comprobante generado.
     */
    @Transactional
    public long generarNumeroComprobante(int empresaId, char tipoSecuencial) {
        // 1. Busca y bloquea el registro en la base de datos para evitar condiciones de carrera
        SecuencialEntity secuencial = secuencialRepository
                .findByNombreAndEmpresaIdWithLock(tipoSecuencial, empresaId)
                .orElseThrow(() -> new SecuencialesNotFoundException("El secuencial '" + tipoSecuencial + "' no se encontró para la empresa " + empresaId));

        // 2. Obtiene el número que le corresponde actualmente
        long numeroGenerado = secuencial.getSiguienteNumero();

        // 3. Incrementa el contador y lo guarda (ejecuta un UPDATE en la tabla secuenciales)
        secuencial.setSiguienteNumero(numeroGenerado + 1);
        secuencialRepository.save(secuencial);

        // 4. Retorna el número generado para la venta
        return numeroGenerado;
    }
}