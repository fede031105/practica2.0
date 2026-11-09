package com.upiiz.practica2.controllers;

import com.upiiz.practica2.models.Entrenador;
import com.upiiz.practica2.services.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    // Helper para generar enlaces HATEOAS de Nivel 3 de Richardson
    private EntityModel<Entrenador> construirHateoasModel(Entrenador entrenador) {
        return EntityModel.of(entrenador,
                linkTo(methodOn(EntrenadorController.class).obtenerPorId(entrenador.getId())).withSelfRel(),
                linkTo(methodOn(EntrenadorController.class).obtenerTodos()).withRel("entrenadores"),
                linkTo(methodOn(EntrenadorController.class).actualizar(entrenador.getId(), entrenador)).withRel("actualizar"),
                linkTo(methodOn(EntrenadorController.class).eliminar(entrenador.getId())).withRel("eliminar")
        );
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Entrenador>>> obtenerTodos() {
        List<EntityModel<Entrenador>> entrenadores = entrenadorService.obtenerTodos().stream()
                .map(this::construirHateoasModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Entrenador>> collectionModel = CollectionModel.of(entrenadores,
                linkTo(methodOn(EntrenadorController.class).obtenerTodos()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Entrenador>> obtenerPorId(@PathVariable Long id) {
        Entrenador entrenador = entrenadorService.obtenerPorId(id);
        return ResponseEntity.ok(construirHateoasModel(entrenador));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Entrenador>> guardar(@Valid @RequestBody Entrenador entrenador) {
        Entrenador nuevoEntrenador = entrenadorService.guardar(entrenador);
        return ResponseEntity.status(HttpStatus.CREATED).body(construirHateoasModel(nuevoEntrenador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Entrenador>> actualizar(@PathVariable Long id, @Valid @RequestBody Entrenador entrenador) {
        Entrenador entrenadorActualizado = entrenadorService.actualizar(id, entrenador);
        return ResponseEntity.ok(construirHateoasModel(entrenadorActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entrenadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<CollectionModel<EntityModel<Entrenador>>> buscarPorNombre(@RequestParam String nombre) {
        List<EntityModel<Entrenador>> entrenadores = entrenadorService.buscarPorNombre(nombre).stream()
                .map(this::construirHateoasModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(entrenadores,
                linkTo(methodOn(EntrenadorController.class).buscarPorNombre(nombre)).withSelfRel(),
                linkTo(methodOn(EntrenadorController.class).obtenerTodos()).withRel("entrenadores")
        ));
    }
}