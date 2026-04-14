package com.citas.especialidades.repositorio;

import com.citas.especialidades.entidad.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para operaciones de base de datos sobre la entidad Especialidad.
 */
@Repository
public interface EspecialidadRepositorio extends JpaRepository<Especialidad, Long> {

    List<Especialidad> findByAreaClinica(String areaClinica);

    boolean existsByNombre(String nombre);

    boolean existsByCodigoEspecialidad(String codigoEspecialidad);

}
