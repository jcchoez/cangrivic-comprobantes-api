package com.facturas.cangrivic.persistence.entity;
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
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"fechaCreacion", "fechaModificacion"})
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Integer rolId;

    @Column(name = "rol_nombre" , nullable = false, unique = true)
    private String  rolNombre;

    @Column(name = "rol_descripcion")
    private String rolDescripcion;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("rol-usuarios")
    private List<UsuarioEntity> usuarios;



    @CreationTimestamp // Se asigna automáticamente al crear el usuario
    @Column(name = "fecha_creacion", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "America/Guayaquil") // Formato de fecha
    private Date fechaCreacion;


    @UpdateTimestamp // Se actualiza automáticamente al modificar el usuario
    @Column(name = "fecha_modificacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "America/Guayaquil") // Formato de fecha
    private Date fechaModificacion;


}
