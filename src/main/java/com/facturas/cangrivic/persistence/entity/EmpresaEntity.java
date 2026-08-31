package com.facturas.cangrivic.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "empresa", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ruc"}), // Evita duplicados en el RUC
        @UniqueConstraint(columnNames = {"empresa_email"}) // Evita duplicados en el email
})
@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties({ "empresaRuc", "empresaDireccion", "empresaTelefono", "empresaEmail", "empresaRepresentanteLegal", "clientes", "productos", "facturas", "fechaCreacion", "fechaModificacion"})
//@JsonIgnoreProperties({"clientes", "facturas", "productos","usuarios", "fechaCreacion", "fechaModificacion"})
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Integer empresaId;

    @Column(name = "empresa_nombre", nullable = false, length = 255)
    private String empresaNombre;

    @Column(name = "ruc", nullable = false, unique = true, length = 20)
    private String empresaRuc;

    @Column(name = "empresa_direccion", columnDefinition = "TEXT")
    private String empresaDireccion;

    @Column(name = "empresa_telefono", length = 20)
    private String empresaTelefono;

    @Column(name = "empresa_email", unique = true, length = 100)
    private String empresaEmail;

    @Column(name = "empresa_representante_legal", length = 255)
    private String empresaRepresentanteLegal;

    @Column(name = "empresa_estado", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean empresaEstado = true;


    // NUEVOS CAMPOS AGREGADOS
    @Column(name = "cert_path", length = 255)
    private String certPath;

    @Column(name = "cert_password", length = 50)
    private String certPassword = "Thiago02";

    @Column(name = "ambiente_sri", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String ambienteSri = "1";

    @Column(name = "tipo_emision_sri", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String tipoEmisionSri = "1";

    @Column(name = "establecimiento", length = 3)
    private String establecimiento = "001";

    @Column(name = "punto_emision", length = 3)
    private String puntoEmision = "100";

    @Column(name = "obligado_contabilidad", columnDefinition = "CHAR(2) DEFAULT 'SI'")
    private String obligadoContabilidad = "SI";



    /*relaciones Inversas para la tabla empresa */
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("empresa-clientes")
    private List<ClienteEntity> clientes;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("empresa-usuarios")
    private List<UsuarioEntity> usuarios;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("empresa-productos")
    private List<ProductoEntity> productos;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("empresa-facturas")
    private List<FacturaEntity> facturas;


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




