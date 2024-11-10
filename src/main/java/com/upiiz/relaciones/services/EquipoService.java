package com.upiiz.relaciones.services;

import com.upiiz.relaciones.entities.Equipo;
import com.upiiz.relaciones.repositories.EquipoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {
    @Autowired
    EquipoRepository equipoRepository;

    public List<Equipo> obtenerTodos() {
        return equipoRepository.findAll();
    }

    public Equipo guardarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Optional<Equipo> obtenerEquipoPorId(Long id) {
        return Optional.ofNullable(equipoRepository.findEquipoById(id));
    }

    @Transactional
    public void deleteEquipo(Long id) {
        equipoRepository.deleteById(id);
    }

    public Equipo actualizarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
}
