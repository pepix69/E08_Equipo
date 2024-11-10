package com.upiiz.relaciones.services;

import com.upiiz.relaciones.entities.Entrenador;
import com.upiiz.relaciones.repositories.EntrenadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {
    @Autowired
    EntrenadorRepository entrenadorRepository;

    public List<Entrenador> obtenerTodos() {
        return entrenadorRepository.findAll();
    }

    public Entrenador guardarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Optional<Entrenador> obtenerEntrenadorPorId(Long id) {
        return Optional.ofNullable(entrenadorRepository.findEntrenadorById(id));
    }

    @Transactional
    public void deleteEntrenador(Long id) {
        entrenadorRepository.deleteById(id);
    }

    public Entrenador actualizarEntrenador(Entrenador equipo) {
        return entrenadorRepository.save(equipo);
    }
}
