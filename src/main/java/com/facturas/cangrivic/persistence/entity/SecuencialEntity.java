package com.facturas.cangrivic.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entidad para la tabla 'secuenciales' (el catálogo y contador)
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

    @Column(name = "siguiente_numero")
    private Long siguienteNumero; // Próximo número a emitir para esta empresa y tipo
}