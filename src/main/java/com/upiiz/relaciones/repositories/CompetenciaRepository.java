package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    @Query("SELECT c FROM Competencia c WHERE c.id = :id")
    Competencia findCompetenciaEntityById(@Param("id") Long id);
}
