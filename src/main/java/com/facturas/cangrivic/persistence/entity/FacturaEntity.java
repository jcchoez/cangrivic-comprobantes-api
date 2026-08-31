package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer facturaId;

    @Column(name = "factura_numero", nullable = false, unique = true)
    private String facturaNumero;

    @Column(name = "factura_total")
    private Double facturaTotal = 0.0;

    @Column(name = "factura_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facturaFecha;

    @Column(name = "factura_clave_acceso", nullable = false, unique = true)
    private String facturaClaveAcceso;

    @Enumerated(EnumType.STRING) // Mapea el enum como una cadena en la base de datos
    @Column(name = "factura_estado")
    private EstadoFactura facturaEstado = EstadoFactura.PENDIENTE; // Valor por defecto




    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference("empresa-facturas")
    private EmpresaEntity empresa;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-facturas")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonBackReference("cliente-facturas")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("factura-detalle_factura")
    private List<FacturaDetalleEntity> detalles;





    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Guayaquil")
    private Date fechaModificacion;

    // Definición del enum EstadoFactura
    public enum EstadoFactura {
        PENDIENTE, AUTORIZADA, RECHAZADA
    }


}
