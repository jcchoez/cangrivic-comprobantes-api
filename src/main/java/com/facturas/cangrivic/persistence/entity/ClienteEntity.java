/*package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties({"fechaCreacion", "fechaModificacion"})
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(name = "cliente_identificacion", nullable = false, unique = true)
    private String clienteIdentificacion;

    @Column(name = "cliente_telefono")
    private String clienteTelefono;

    @Column(name = "cliente_direccion")
    private String clienteDireccion;

    @Column(name = "cliente_disabled", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean clienteDisabled = false;

    @Column(name = "cliente_correo",nullable = false, unique = true)
    private String clienteCorreo; // ← Nuevo campo agregado

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference("empresa-clientes")
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonManagedReference("cliente-facturas")
    private List<FacturaEntity> facturas;




    @CreationTimestamp // Se asigna automáticamente al crear el usuario
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "America/Guayaquil") // Formato de fecha
    private Date fechaCreacion;





    @UpdateTimestamp // Se actualiza automáticamente al modificar el usuario
    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "America/Guayaquil") // Formato de fecha
    private Date fechaModificacion;
}*/

package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "cliente",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cliente_correo_empresa",
                        columnNames = {"cliente_correo", "empresa_id"}
                ),
                @UniqueConstraint(
                        name = "uk_cliente_identificacion_empresa",
                        columnNames = {"cliente_identificacion", "empresa_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(name = "cliente_identificacion", nullable = false)
    private String clienteIdentificacion;

    @Column(name = "cliente_telefono")
    private String clienteTelefono;

    @Column(name = "cliente_direccion")
    private String clienteDireccion;

    @Column(
            name = "cliente_disabled",
            nullable = false,
            columnDefinition = "TINYINT(1) DEFAULT 0"
    )
    private Boolean clienteDisabled = false;

    @Column(name = "cliente_correo", nullable = false)
    private String clienteCorreo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference("empresa-clientes")
    private EmpresaEntity empresa;

    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    @JsonManagedReference("cliente-facturas")
    private List<FacturaEntity> facturas;

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

