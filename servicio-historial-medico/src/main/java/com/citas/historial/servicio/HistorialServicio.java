package com.citas.historial.servicio;

import com.citas.historial.dto.HistorialDto;
import com.citas.historial.entidad.HistorialMedico;

import java.util.List;

/**
 * Contrato del servicio de negocio para la gestión del historial médico.
 * Los registros son de solo lectura una vez creados (sin update ni delete).
 */
public interface HistorialServicio {

    HistorialMedico registrar(HistorialDto dto);

    List<HistorialMedico> listarTodos();

    HistorialMedico obtenerPorId(Long id);

    HistorialMedico obtenerPorCitaId(Long citaId);

    List<HistorialMedico> listarPorPaciente(Long pacienteId);

    List<HistorialMedico> listarPorMedico(Long medicoId);

}
