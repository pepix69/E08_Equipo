package com.upiiz.relaciones.services;

import com.upiiz.relaciones.entities.Liga;
import com.upiiz.relaciones.repositories.LigaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {
    @Autowired
    LigaRepository ligaRepository;

    public List<Liga> obtenerTodos() {
        return ligaRepository.findAll();
    }

    public Liga guardarLiga(Liga competencia) {
        return ligaRepository.save(competencia);
    }

    public Optional<Liga> obtenerLigaPorId(Long id) {
        return Optional.ofNullable(ligaRepository.findLigaById(id));
    }

    @Transactional
    public void deleteLiga(Long id) {
        ligaRepository.deleteById(id);
    }

    public Liga actualizarLiga(Liga equipo) {
        return ligaRepository.save(equipo);
    }
}
