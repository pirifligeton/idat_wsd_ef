package com.citas.medicos.repositorio;

import com.citas.medicos.entidad.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para operaciones de base de datos sobre la entidad Medico.
 */
@Repository
public interface MedicoRepositorio extends JpaRepository<Medico, Long> {

    List<Medico> findByEspecialidadId(Long especialidadId);

    Optional<Medico> findByNumeroColegiado(String numeroColegiado);

    boolean existsByNumeroColegiado(String numeroColegiado);

    boolean existsByCorreoElectronico(String correoElectronico);

}
