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
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties({"fechaCreacion", "fechaModificacion"})
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "usuario_nombre", nullable = false)
    private String usuarioNombre;

    @Column(name = "usuario_email" ,nullable = false)
    private String usuarioEmail;

    @Column(name = "usuario_telefono" ,nullable = false)
    private String usuarioTelefono;

    @Column(name = "usuario_username", nullable = false, unique = true)
    private String usuarioUsername;

    @Column(name = "usuario_password", nullable = false)
    private String usuarioPassword;

    @Column(name = "usuario_disabled", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean usuarioDisabled = false;

    @Column(name = "usuario_locked", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean usuarioLocked = false;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    @JsonBackReference("empresa-usuarios") //SE HOZCO CAMBIO  JsonBackReference  POR  JsonBackReference
    private EmpresaEntity empresa;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    @JsonBackReference("rol-usuarios")
    private RolEntity rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-facturas")
    private List<FacturaEntity> facturas;



    @CreationTimestamp // Se asigna automáticamente al crear el usuario
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "America/Guayaquil") // Formato de fecha
    private Date fechaCreacion;

    // @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp // Se actualiza automáticamente al modificar el usuario
    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "America/Guayaquil") // Formato de fecha
    private Date fechaModificacion;


    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "usuarioId=" + usuarioId +
                ", usuarioNombre='" + usuarioNombre + '\'' +
                ", usuarioEmail='" + usuarioEmail + '\'' +
                ", usuarioTelefono='" + usuarioTelefono + '\'' +
                ", usuarioUsername='" + usuarioUsername + '\'' +
                ", usuarioPassword='" + usuarioPassword + '\'' +
                ", usuarioDisabled=" + usuarioDisabled +
                ", usuarioLocked=" + usuarioLocked +
                ", empresa=" + empresa +
                ", rol=" + rol +
                '}';
    }
}
