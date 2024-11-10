package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.Entrenador;
import com.upiiz.relaciones.responses.EntrenadorCustomResponse;
import com.upiiz.relaciones.services.EntrenadorService;
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
@RequestMapping("/api/v1/entrenadores")
@Tag(
        name = "Entrenadores"
)
public class EntrenadorController {
    @Autowired
    EntrenadorService entrenadorService;

    @GetMapping
    public ResponseEntity<EntrenadorCustomResponse<List<Entrenador>>> getEntrenadores() {
        List<Entrenador> entrenadores = new ArrayList<>();
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenadores = entrenadorService.obtenerTodos();
            if (!entrenadores.isEmpty()) {
                EntrenadorCustomResponse<List<Entrenador>> response = new EntrenadorCustomResponse<>(1, "Entrenadores encontrados", entrenadores, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntrenadorCustomResponse<>(6, "Entrenadores no encontrados", entrenadores, links));
            }
        } catch (Exception e) {
            EntrenadorCustomResponse<List<Entrenador>> response = new EntrenadorCustomResponse<>(8, "Error interno del servidor", entrenadores, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorCustomResponse<Entrenador>> getEntrenadorById(@PathVariable Long id) {
        Optional<Entrenador> entrenador = Optional.empty();
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenador = entrenadorService.obtenerEntrenadorPorId(id);
            if (entrenador.isPresent()) {
                EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(1, "Entrenador encontrado", entrenador.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntrenadorCustomResponse<>(0, "Entrenador no encontrado", null, links));
            }
        } catch (Exception e) {
            EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(8, "Error interno del servidor", null, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<EntrenadorCustomResponse<Entrenador>> crearEntrenador(@RequestBody Entrenador entrenador) {
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            Entrenador entrenadorEntity = entrenadorService.guardarEntrenador(entrenador);
            if (entrenadorEntity != null) {
                EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(1, "Entrenador creado", entrenadorEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntrenadorCustomResponse<>(6, "Error al crear el entrenador", entrenadorEntity, links));
            }
        } catch (Exception e) {
            EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(8, "Error interno del servidor", null, links);
            System.out.println("Error en Post Entrenador: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntrenadorCustomResponse<Entrenador>> deleteEntrenadorById(@PathVariable Long id) {
        Optional<Entrenador> entrenador = Optional.empty();
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenador = entrenadorService.obtenerEntrenadorPorId(id);
            if (entrenador.isPresent()) {
                entrenadorService.deleteEntrenador(id);
                return ResponseEntity.status(HttpStatus.OK).body(new EntrenadorCustomResponse<>(1, "Entrenador eliminado", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntrenadorCustomResponse<>(0, "Entrenador no encontrado", null, links));
            }
        } catch (Exception e) {
            EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorCustomResponse<Entrenador>> updateEntrenador(@RequestBody Entrenador entrenador, @PathVariable Long id) {
        Link allEntrenadoresLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLink);
        try {
            entrenador.setId(id);
            if (entrenadorService.obtenerEntrenadorPorId(id).isPresent()) {
                Entrenador entrenadorActualizado = entrenadorService.actualizarEntrenador(entrenador);
                EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(1, "Entrenador actualizado", entrenadorActualizado, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntrenadorCustomResponse<>(0, "Entrenador no encontrado", null, links));
            }
        } catch (Exception e) {
            EntrenadorCustomResponse<Entrenador> response = new EntrenadorCustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

