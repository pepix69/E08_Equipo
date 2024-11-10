package com.upiiz.relaciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne(targetEntity = Liga.class)
    private Liga liga;

    @OneToMany(targetEntity = Jugador.class, fetch = FetchType.LAZY, mappedBy = "equipo")
    private List<Jugador> jugador;

    @OneToOne(targetEntity = Entrenador.class)
    private Entrenador entrenador;

    @ManyToMany(targetEntity = Competencia.class, fetch = FetchType.LAZY)
    private List<Competencia> competencias;
}
