package com.citas.especialidades.servicio;

import com.citas.especialidades.dto.EspecialidadDetalleDto;
import com.citas.especialidades.dto.EspecialidadDto;
import com.citas.especialidades.entidad.Especialidad;

import java.util.List;

/**
 * Contrato del servicio de negocio para la gestión de especialidades médicas.
 */
public interface EspecialidadServicio {

    List<Especialidad> listarTodas();

    Especialidad obtenerPorId(Long id);

    EspecialidadDetalleDto obtenerConMedicos(Long id);

    List<Especialidad> listarPorAreaClinica(String areaClinica);

    Especialidad registrar(EspecialidadDto dto);

    Especialidad actualizar(Long id, EspecialidadDto dto);

    void eliminar(Long id);

}
