package com.citas.citas.servicio;

import com.citas.citas.dto.CambioEstadoDto;
import com.citas.citas.dto.CitaDetalleDto;
import com.citas.citas.dto.CitaDto;
import com.citas.citas.entidad.Cita;

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

    Cita cambiarEstado(Long id, CambioEstadoDto dto);

    void eliminar(Long id);

}
