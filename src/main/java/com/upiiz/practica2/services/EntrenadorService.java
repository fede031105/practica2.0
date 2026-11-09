package com.upiiz.practica2.services;

import com.upiiz.practica2.exceptions.ResourceNotFoundException;
import com.upiiz.practica2.models.Entrenador;
import com.upiiz.practica2.repositories.EntrenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;

    public EntrenadorService(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    public List<Entrenador> obtenerTodos() {
        return entrenadorRepository.findAll();
    }

    public Entrenador obtenerPorId(Long id) {
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrenador no encontrado con ID: " + id));
    }

    public Entrenador guardar(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador actualizar(Long id, Entrenador entrenadorDetalles) {
        Entrenador entrenador = obtenerPorId(id);
        entrenador.setNombre(entrenadorDetalles.getNombre());
        entrenador.setEspecialidad(entrenadorDetalles.getEspecialidad());
        return entrenadorRepository.save(entrenador);
    }

    public void eliminar(Long id) {
        Entrenador entrenador = obtenerPorId(id);
        entrenadorRepository.delete(entrenador);
    }

    public List<Entrenador> buscarPorNombre(String nombre) {
        return entrenadorRepository.findByNombreContainingIgnoreCase(nombre);
    }
}