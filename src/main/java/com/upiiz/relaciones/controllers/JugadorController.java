package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.entities.Jugador;
import com.upiiz.relaciones.responses.JugadorCustomResponse;
import com.upiiz.relaciones.services.JugadorService;
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
@RequestMapping("/api/v1/jugadores")
@Tag(
        name = "Jugadores"
)
public class JugadorController {
    @Autowired
    JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<JugadorCustomResponse<List<Jugador>>> getJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        Link allJugadoresLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadoresLink);
        try {
            jugadores = jugadorService.obtenerTodos();
            if (!jugadores.isEmpty()) {
                JugadorCustomResponse<List<Jugador>> response = new JugadorCustomResponse<>(1, "Jugadores encontrados", jugadores, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JugadorCustomResponse<>(6, "Jugadores no encontrados", jugadores, links));
            }
        } catch (Exception e) {
            JugadorCustomResponse<List<Jugador>> response = new JugadorCustomResponse<>(8, "Error interno del servidor", jugadores, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorCustomResponse<Jugador>> getJugadorById(@PathVariable Long id) {
        Optional<Jugador> jugador = Optional.empty();
        Link allJugadoresLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadoresLink);
        try {
            jugador = jugadorService.obtenerJugadorPorId(id);
            if (jugador.isPresent()) {
                JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(1, "Jugador encontrado", jugador.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JugadorCustomResponse<>(0, "Jugador no encontrado", null, links));
            }
        } catch (Exception e) {
            JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(8, "Error interno del servidor", null, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<JugadorCustomResponse<Jugador>> crearJugador(@RequestBody Jugador jugador) {
        Link allJugadoresLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadoresLink);
        try {
            Jugador jugadorEntity = jugadorService.guardarJugador(jugador);
            if (jugadorEntity != null) {
                JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(1, "Jugador creado", jugadorEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JugadorCustomResponse<>(6, "Error al crear el jugador", jugadorEntity, links));
            }
        } catch (Exception e) {
            JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(8, "Error interno del servidor", null, links);
            System.out.println("Error en Post Jugador: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JugadorCustomResponse<Jugador>> deleteJugadorById(@PathVariable Long id) {
        Optional<Jugador> jugador = Optional.empty();
        Link allJugadoresLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadoresLink);
        try {
            jugador = jugadorService.obtenerJugadorPorId(id);
            if (jugador.isPresent()) {
                jugadorService.deleteJugador(id);
                return ResponseEntity.status(HttpStatus.OK).body(new JugadorCustomResponse<>(1, "Jugador eliminado", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JugadorCustomResponse<>(0, "Jugador no encontrado", null, links));
            }
        } catch (Exception e) {
            JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorCustomResponse<Jugador>> updateJugador(@RequestBody Jugador jugador, @PathVariable Long id) {
        Link allJugadoresLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allJugadoresLink);
        try {
            jugador.setId(id);
            if (jugadorService.obtenerJugadorPorId(id).isPresent()) {
                Jugador jugadorActualizado = jugadorService.actualizarJugador(jugador);
                JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(1, "Jugador actualizado", jugadorActualizado, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JugadorCustomResponse<>(0, "Jugador no encontrado", null, links));
            }
        } catch (Exception e) {
            JugadorCustomResponse<Jugador> response = new JugadorCustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

