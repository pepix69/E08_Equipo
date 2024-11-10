package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.entities.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {
    //Get by ID
    @Query("SELECT l FROM Liga l WHERE l.id = :id")
    Liga findLigaById(@Param("id") Long id);
}
