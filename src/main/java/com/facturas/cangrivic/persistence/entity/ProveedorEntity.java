package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(
        name = "proveedor",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_proveedor_correo_empresa",
                        columnNames = {"proveedor_correo", "empresa_id"}
                ),
                @UniqueConstraint(
                        name = "uk_proveedor_identificacion_empresa",
                        columnNames = {"proveedor_identificacion", "empresa_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proveedor_id")
    private Integer proveedorId;

    @Column(name = "proveedor_nombre", nullable = false)
    private String proveedorNombre;

    @Column(name = "proveedor_identificacion", nullable = false)
    private String proveedorIdentificacion;

    @Column(name = "proveedor_telefono")
    private String proveedorTelefono;

    @Column(name = "proveedor_direccion")
    private String proveedorDireccion;

    @Column(
            name = "proveedor_disabled",
            nullable = false,
            columnDefinition = "TINYINT(1) DEFAULT 0"
    )
    private Boolean proveedorDisabled = false;

    @Column(name = "proveedor_correo", nullable = false)
    private String proveedorCorreo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference("empresa-proveedores")
    private EmpresaEntity empresa;

    // Si necesitas relación con facturas, descomenta:
    // @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnore
    // private List<FacturaEntity> facturas;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "America/Guayaquil"
    )
    private Date fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "America/Guayaquil"
    )
    private Date fechaModificacion;
}