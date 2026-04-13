package com.citas.pacientes.servicio;

import com.citas.pacientes.dto.PacienteDto;
import com.citas.pacientes.entidad.Paciente;

import java.util.List;

/**
 * Contrato del servicio de negocio para la gestión de pacientes.
 */
public interface PacienteServicio {

    List<Paciente> listarTodos();

    Paciente obtenerPorId(Long id);

    Paciente registrar(PacienteDto dto);

    Paciente actualizar(Long id, PacienteDto dto);

    void eliminar(Long id);

}
