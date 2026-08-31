package com.facturas.cangrivic.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Entidad para la tabla 'secuenciales_logs' (el generador de números)
@Entity
@Getter
@Setter
@Table(name = "secuenciales_logs")
@NoArgsConstructor
public class SecuencialLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Long numero; // This field will be the primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secuencial_id")
    private SecuencialEntity secuencial;

    @Column(name = "empresa_id")
    private Integer empresaId;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    // Getters y setters...
}
