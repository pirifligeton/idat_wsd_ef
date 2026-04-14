package com.citas.citas.repositorio;

import com.citas.citas.entidad.Cita;
import com.citas.citas.entidad.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para operaciones de base de datos sobre la entidad Cita.
 */
@Repository
public interface CitaRepositorio extends JpaRepository<Cita, Long> {

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByMedicoId(Long medicoId);

    List<Cita> findByEstado(EstadoCita estado);

    List<Cita> findByFechaCita(LocalDate fechaCita);

    boolean existsByPacienteIdAndMedicoIdAndFechaCitaAndHoraCita(
            Long pacienteId, Long medicoId,
            java.time.LocalDate fechaCita, java.time.LocalTime horaCita);

}
