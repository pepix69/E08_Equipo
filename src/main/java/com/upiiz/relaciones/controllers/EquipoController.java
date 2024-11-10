package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.Equipo;
import com.upiiz.relaciones.responses.EquipoCustomResponse;
import com.upiiz.relaciones.services.EquipoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/equipos")
@Tag(
        name = "Equipo"
)
public class EquipoController {
    @Autowired
    EquipoService equipoService;

    @GetMapping
    public ResponseEntity<EquipoCustomResponse<List<Equipo>>> getEquipos() {
        List<Equipo> equipos = new ArrayList<>();
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            equipos = equipoService.obtenerTodos();
            if (!equipos.isEmpty()) {
                EquipoCustomResponse<List<Equipo>> response = new EquipoCustomResponse<>(1, "Equipos encontrados", equipos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EquipoCustomResponse<>(6, "Equipos no encontrados", equipos, links));
            }
        } catch (Exception e) {
            EquipoCustomResponse<List<Equipo>> response = new EquipoCustomResponse<>(8, "Error interno del servidor", equipos, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoCustomResponse<Equipo>> getEquipoById(@PathVariable Long id) {
        Optional<Equipo> equipo = Optional.empty();
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            equipo = equipoService.obtenerEquipoPorId(id);
            if (equipo.isPresent()) {
                EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(1, "Equipo encontrado", equipo.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EquipoCustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(8, "Error interno del servidor", null, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<EquipoCustomResponse<Equipo>> crearEquipo(@RequestBody Equipo equipo) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            Equipo equipoEntity = equipoService.guardarEquipo(equipo);
            if (equipoEntity != null) {
                EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(1, "Equipo creado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EquipoCustomResponse<>(6, "Error al crear el equipo", equipoEntity, links));
            }
        } catch (Exception e) {
            EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(8, "Error interno del servidor", null, links);
            System.out.println("Error en Post Equipo: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EquipoCustomResponse<Equipo>> deleteEquipoById(@PathVariable Long id) {
        Optional<Equipo> equipo = Optional.empty();
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            equipo = equipoService.obtenerEquipoPorId(id);
            if (equipo.isPresent()) {
                equipoService.deleteEquipo(id);
                return ResponseEntity.status(HttpStatus.OK).body(new EquipoCustomResponse<>(1, "Equipo eliminado", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EquipoCustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoCustomResponse<Equipo>> updateEquipo(@RequestBody Equipo equipo, @PathVariable Long id) {
        Link allEquiposLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allEquiposLink);
        try {
            equipo.setId(id);
            if (equipoService.obtenerEquipoPorId(id).isPresent()) {
                Equipo equipoActualizado = equipoService.actualizarEquipo(equipo);
                EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(1, "Equipo actualizado", equipoActualizado, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EquipoCustomResponse<>(0, "Equipo no encontrado", null, links));
            }
        } catch (Exception e) {
            EquipoCustomResponse<Equipo> response = new EquipoCustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

