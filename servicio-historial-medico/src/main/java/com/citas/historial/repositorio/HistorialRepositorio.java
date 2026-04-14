package com.citas.historial.repositorio;

import com.citas.historial.entidad.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para operaciones de base de datos sobre la entidad HistorialMedico.
 */
@Repository
public interface HistorialRepositorio extends JpaRepository<HistorialMedico, Long> {

    List<HistorialMedico> findByPacienteId(Long pacienteId);

    List<HistorialMedico> findByMedicoId(Long medicoId);

    Optional<HistorialMedico> findByCitaId(Long citaId);

    boolean existsByCitaId(Long citaId);

}
