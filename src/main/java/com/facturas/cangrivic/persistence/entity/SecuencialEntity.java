package com.facturas.cangrivic.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Entidad para la tabla 'secuenciales' (el catálogo)
@Entity
@Getter
@Setter
@Table(name = "secuenciales")
@NoArgsConstructor
public class SecuencialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secuencial_id")
    private Integer secuencialId;

    @Column(name = "nombre")
    private char nombre;

    @Column(name = "empresa_id")
    private Integer empresaId;

    // Getters y setters...
}