package com.citas.medicos.servicio;

import com.citas.medicos.dto.MedicoDto;
import com.citas.medicos.entidad.Medico;

import java.util.List;

/**
 * Contrato del servicio de negocio para la gestión de médicos.
 */
public interface MedicoServicio {

    List<Medico> listarTodos();

    Medico obtenerPorId(Long id);

    List<Medico> listarPorEspecialidad(String especialidad);

    Medico registrar(MedicoDto dto);

    Medico actualizar(Long id, MedicoDto dto);

    void eliminar(Long id);

}
