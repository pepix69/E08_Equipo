package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    @Query("SELECT eq FROM Equipo eq WHERE eq.id = :id")
    Equipo findEquipoById(@Param("id") Long id);
}
