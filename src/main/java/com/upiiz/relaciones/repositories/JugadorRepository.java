package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    //Get by ID
    @Query("SELECT j FROM Jugador j WHERE j.id = :id")
    Jugador findJugadorById(@Param("id") Long id);
}
