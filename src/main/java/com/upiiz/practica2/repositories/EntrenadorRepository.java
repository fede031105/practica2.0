package com.upiiz.practica2.repositories;

import com.upiiz.practica2.models.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    List<Entrenador> findByNombreContainingIgnoreCase(String nombre);
}