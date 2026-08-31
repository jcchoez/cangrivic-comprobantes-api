package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Long ventaId;

    /*sri*/
    @Column(name = "secuencial", nullable = false, unique = true)
    private String secuencial;

    @Column(name = "codigo_numerico", nullable = false, unique = true)
    private String codigoNumerico;
    /*sri*/

    @NotNull(message = "El ID de la empresa es obligatorio")
    @Min(value = 1, message = "El ID de la empresa debe ser un número positivo")
    @Column(name = "empresa_id", nullable = false)
    private Integer empresaId;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "fecha_venta", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaVenta;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;


    @Column(name = "establecimiento", length = 3)
    private String establecimiento = "001";

    @Column(name = "punto_emision", length = 3)
    private String puntoEmision = "100";


    //sri
    @Column(name = "estado_sri", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'PENDIENTE'")
    private String estadoSri;

    @Column(name = "mensaje_sri", columnDefinition = "TEXT")
    private String mensajeSri;

    @Column(name = "clave_acceso", length = 49)
    private String claveAcceso;

    @Column(name = "numero_autorizacion", length = 49)
    private String numeroAutorizacion;

    @Column(name = "fecha_autorizacion")
    private LocalDateTime fechaAutorizacion;

    @Column(name = "intentos_envio")
    private Integer intentosEnvio;

    @Column(name = "ultimo_intento_envio")
    private LocalDateTime ultimoIntentoEnvio;



    // Tipo de comprobante: 01-Factura, 04-Nota Crédito, 05-Nota Débito, 06-Guía Remisión, 07-Retención
    @Column(name = "tipo_comprobante", length = 2, columnDefinition = "CHAR(2) DEFAULT '01'")
    private String tipoComprobante = "01";

    // Tipo de identificación del comprador: 04-RUC, 05-Cédula, 06-Pasaporte, 07-Consumidor final
    /*@Column(name = "tipo_identificacion_comprador", length = 2, columnDefinition = "CHAR(2) DEFAULT NULL")
    private String tipoIdentificacionComprador;
*/

    // NUEVOS CAMPOS AGREGADOS
    @Column(name = "respuesta_json", columnDefinition = "TEXT")
    private String respuestaJson;

    @Column(name = "correo_enviado", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean correoEnviado = false;

    @Column(name = "fecha_envio_correo")
    private LocalDateTime fechaEnvioCorreo;

    @Column(name = "intentos_envio_correo", columnDefinition = "INT DEFAULT 0")
    private Integer intentosEnvioCorreo = 0;

    @Column(name = "mensaje_envio_correo", columnDefinition = "TEXT")
    private String mensajeEnvioCorreo;

    @Column(name = "ultimo_intento_envio_correo")
    private LocalDateTime ultimoIntentoEnvioCorreo;

    @Column(name = "ruta_copia_correo", length = 500)
    private String rutaCopiaCorreo;








    // Relación con items
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("venta-ventaItems")
    private List<VentaItemEntity> items = new ArrayList<>();


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fechaCreacion;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fechaModificacion;
  
}

