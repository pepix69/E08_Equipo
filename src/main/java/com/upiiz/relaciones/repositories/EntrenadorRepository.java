package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    @Query("SELECT en FROM Entrenador en WHERE en.id = :id")
    Entrenador findEntrenadorById(@Param("id") Long id);
}
