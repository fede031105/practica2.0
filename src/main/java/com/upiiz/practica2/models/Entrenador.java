package com.upiiz.practica2.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "entrenadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especialidad;
}