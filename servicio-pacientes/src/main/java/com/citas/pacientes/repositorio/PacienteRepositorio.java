package com.citas.pacientes.repositorio;

import com.citas.pacientes.entidad.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para operaciones de base de datos sobre la entidad Paciente.
 */
@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByNumeroCedula(String numeroCedula);

    boolean existsByNumeroCedula(String numeroCedula);

    boolean existsByCorreoElectronico(String correoElectronico);

}
