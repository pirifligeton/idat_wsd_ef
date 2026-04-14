package com.citas.historial.servicio;

import com.citas.historial.dto.HistorialDto;
import com.citas.historial.entidad.HistorialMedico;
import com.citas.historial.excepcion.RecursoNoEncontradoException;
import com.citas.historial.excepcion.RecursoYaExisteException;
import com.citas.historial.repositorio.HistorialRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de historial médico.
 *
 * Regla de negocio principal:
 * - Solo se puede registrar un historial por cita (citaId único).
 * - Los registros son inmutables: no existen métodos de actualización ni eliminación.
 */
@Service
public class HistorialServicioImpl implements HistorialServicio {

    private final HistorialRepositorio repositorio;

    public HistorialServicioImpl(HistorialRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public HistorialMedico registrar(HistorialDto dto) {
        // Regla: solo puede existir un historial por cita
        if (repositorio.existsByCitaId(dto.getCitaId())) {
            throw new RecursoYaExisteException(
                    "Ya existe un registro de historial para la cita con id: " + dto.getCitaId());
        }

        HistorialMedico nuevoRegistro = new HistorialMedico(
                dto.getCitaId(),
                dto.getPacienteId(),
                dto.getMedicoId(),
                dto.getDiagnostico(),
                dto.getTratamiento(),
                dto.getObservaciones()
        );

        return repositorio.save(nuevoRegistro);
    }

    @Override
    public List<HistorialMedico> listarTodos() {
        return repositorio.findAll();
    }

    @Override
    public HistorialMedico obtenerPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Registro de historial no encontrado con id: " + id));
    }

    @Override
    public HistorialMedico obtenerPorCitaId(Long citaId) {
        return repositorio.findByCitaId(citaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe un historial registrado para la cita con id: " + citaId));
    }

    @Override
    public List<HistorialMedico> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    @Override
    public List<HistorialMedico> listarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId);
    }

}
