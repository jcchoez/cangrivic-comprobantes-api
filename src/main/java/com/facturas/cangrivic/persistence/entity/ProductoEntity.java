package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Integer productoId;

    @Column(name = "producto_nombre", nullable = false)
    private String productoNombre;


    @Column(name = "producto_codigo", nullable = false, unique = true)
    private String productoCodigo;

    @Column(name = "producto_descripcion")
    private String productoDescripcion;

    @Column(name = "producto_precio", nullable = false)
    private Double productoPrecio;

    @Column(name = "producto_stock", nullable = false)
    private Integer productoStock = 0;

    @Column(name = "producto_estado", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean productoEstado = true;

    @Column(name = "producto_disabled", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean productoDisabled = false;


    // NUEVO CAMPO IVA
    @Column(name = "iva", nullable = false, precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal iva = BigDecimal.ZERO;


    // NUEVOS CAMPOS SRI
    @Column(name = "producto_sri_tarifa", length = 10, columnDefinition = "varchar(10) DEFAULT '0%'")
    private String productoSriTarifa = "0%";

    @Column(name = "producto_sri_iva_turismo", length = 1, columnDefinition = "char(1) DEFAULT 'N' COMMENT 'Aplica tarifa IVA turismo: S/N'")
    private String productoSriIvaTurismo = "N";

    @Column(name = "producto_sri_ice", length = 1, columnDefinition = "char(1) DEFAULT 'N' COMMENT 'Aplica ICE: S/N'")
    private String productoSriIce = "N";

    @Column(name = "producto_sri_codigo_ice", length = 10, columnDefinition = "varchar(10) DEFAULT NULL COMMENT 'Código ICE'")
    private String productoSriCodigoIce;



    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference("empresa-productos")
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("producto-detalle_factura")
    private List<FacturaDetalleEntity> detallesFactura;



    @Column(name = "fecha_creacion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

}
