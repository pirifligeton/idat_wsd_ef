package com.citas.citas.servicio;

import com.citas.citas.dto.CitaDetalleDto;
import com.citas.citas.dto.CitaDto;
import com.citas.citas.entidad.Cita;
import com.citas.citas.entidad.EstadoCita;

import java.util.List;

/**
 * Contrato del servicio de negocio para la gestión de citas médicas.
 */
public interface CitaServicio {

    List<Cita> listarTodas();

    CitaDetalleDto obtenerDetallePorId(Long id);

    List<Cita> listarPorPaciente(Long pacienteId);

    List<Cita> listarPorMedico(Long medicoId);

    Cita registrar(CitaDto dto);

    Cita actualizar(Long id, CitaDto dto);

    Cita cambiarEstado(Long id, EstadoCita nuevoEstado);

    void eliminar(Long id);

}
