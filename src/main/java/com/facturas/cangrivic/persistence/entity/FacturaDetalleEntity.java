package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "factura_detalle")
@Getter
@Setter
@NoArgsConstructor
public class FacturaDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_detalle_id")
    private Integer facturaDetalleId;

    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = false)
    @JsonBackReference("factura-detalle_factura")
    private FacturaEntity factura;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference("producto-detalle_factura")
    private ProductoEntity producto;



    @Column(name = "detalle_cantidad", nullable = false)
    private Integer detalleCantidad;

    @Column(name = "detalle_precio_unitario", nullable = false)
    private Double detallePrecioUnitario;

    @Column(name = "detalle_subtotal", insertable = false, updatable = false)
    private Double detalleSubtotal;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fechaModificacion;
}
