package com.citas.citas.servicio;

import com.citas.citas.cliente.HistorialCliente;
import com.citas.citas.cliente.MedicoCliente;
import com.citas.citas.cliente.PacienteCliente;
import com.citas.citas.dto.*;
import com.citas.citas.entidad.Cita;
import com.citas.citas.entidad.EstadoCita;
import com.citas.citas.excepcion.RecursoNoEncontradoException;
import com.citas.citas.excepcion.RecursoYaExisteException;
import com.citas.citas.excepcion.ServicioNoDisponibleException;
import com.citas.citas.repositorio.CitaRepositorio;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de citas médicas.
 * Se comunica con servicio-pacientes, servicio-medicos y servicio-historial-medico
 * mediante OpenFeign.
 *
 * Cuando una cita es marcada como COMPLETADA:
 *  1. Valida que los campos clínicos no sean nulos.
 *  2. Guarda los campos clínicos en la entidad Cita.
 *  3. Dispara automáticamente un POST a servicio-historial-medico.
 */
@Service
public class CitaServicioImpl implements CitaServicio {

    private final CitaRepositorio repositorio;
    private final PacienteCliente pacienteCliente;
    private final MedicoCliente medicoCliente;
    private final HistorialCliente historialCliente;

    public CitaServicioImpl(CitaRepositorio repositorio,
                            PacienteCliente pacienteCliente,
                            MedicoCliente medicoCliente,
                            HistorialCliente historialCliente) {
        this.repositorio = repositorio;
        this.pacienteCliente = pacienteCliente;
        this.medicoCliente = medicoCliente;
        this.historialCliente = historialCliente;
    }

    @Override
    public List<Cita> listarTodas() {
        return repositorio.findAll();
    }

    @Override
    public CitaDetalleDto obtenerDetallePorId(Long id) {
        Cita cita = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        PacienteDto paciente;
        MedicoDto medico;
        try {
            paciente = pacienteCliente.obtenerPorId(cita.getPacienteId());
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException("El paciente con id " + cita.getPacienteId() + " ya no existe en el sistema.");
        }
        try {
            medico = medicoCliente.obtenerPorId(cita.getMedicoId());
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException("El médico con id " + cita.getMedicoId() + " ya no existe en el sistema.");
        }

        CitaDetalleDto detalle = new CitaDetalleDto();
        detalle.setId(cita.getId());
        detalle.setFechaCita(cita.getFechaCita());
        detalle.setHoraCita(cita.getHoraCita());
        detalle.setMotivo(cita.getMotivo());
        detalle.setEstado(cita.getEstado());
        detalle.setPaciente(paciente);
        detalle.setMedico(medico);

        return detalle;
    }

    @Override
    public List<Cita> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    @Override
    public List<Cita> listarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId);
    }

    @Override
    public Cita registrar(CitaDto dto) {
        // Validar existencia del paciente
        try {
            pacienteCliente.obtenerPorId(dto.getPacienteId());
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException("No existe un paciente con id: " + dto.getPacienteId());
        } catch (FeignException ex) {
            throw new ServicioNoDisponibleException("El servicio de pacientes no está disponible en este momento.");
        }

        // Validar existencia del médico
        try {
            medicoCliente.obtenerPorId(dto.getMedicoId());
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException("No existe un médico con id: " + dto.getMedicoId());
        } catch (FeignException ex) {
            throw new ServicioNoDisponibleException("El servicio de médicos no está disponible en este momento.");
        }

        // Validar duplicado
        boolean existeDuplicado = repositorio.existsByPacienteIdAndMedicoIdAndFechaCitaAndHoraCita(
                dto.getPacienteId(), dto.getMedicoId(), dto.getFechaCita(), dto.getHoraCita()
        );
        if (existeDuplicado) {
            throw new RecursoYaExisteException("Ya existe una cita registrada para ese médico en esa fecha y hora.");
        }

        EstadoCita estadoInicial = dto.getEstado() != null ? dto.getEstado() : EstadoCita.PENDIENTE;

        Cita nuevaCita = new Cita(
                dto.getPacienteId(),
                dto.getMedicoId(),
                dto.getFechaCita(),
                dto.getHoraCita(),
                dto.getMotivo(),
                estadoInicial
        );

        return repositorio.save(nuevaCita);
    }

    @Override
    public Cita actualizar(Long id, CitaDto dto) {
        Cita citaExistente = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        citaExistente.setPacienteId(dto.getPacienteId());
        citaExistente.setMedicoId(dto.getMedicoId());
        citaExistente.setFechaCita(dto.getFechaCita());
        citaExistente.setHoraCita(dto.getHoraCita());
        citaExistente.setMotivo(dto.getMotivo());

        if (dto.getEstado() != null) {
            citaExistente.setEstado(dto.getEstado());
        }

        return repositorio.save(citaExistente);
    }

    @Override
    public Cita cambiarEstado(Long id, CambioEstadoDto dto) {
        Cita cita = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        if (dto.getNuevoEstado() == EstadoCita.COMPLETADA) {
            // Validar que los campos clínicos estén presentes
            if (dto.getDiagnostico() == null || dto.getDiagnostico().isBlank()) {
                throw new RecursoYaExisteException("El campo 'diagnostico' es obligatorio para completar una cita.");
            }
            if (dto.getTratamiento() == null || dto.getTratamiento().isBlank()) {
                throw new RecursoYaExisteException("El campo 'tratamiento' es obligatorio para completar una cita.");
            }

            // Guardar campos clínicos en la cita
            cita.setDiagnostico(dto.getDiagnostico());
            cita.setTratamiento(dto.getTratamiento());
            cita.setObservaciones(dto.getObservaciones());
            cita.setEstado(EstadoCita.COMPLETADA);
            Cita citaGuardada = repositorio.save(cita);

            // Disparar POST a servicio-historial-medico
            try {
                HistorialDto historialDto = new HistorialDto(
                        citaGuardada.getId(),
                        citaGuardada.getPacienteId(),
                        citaGuardada.getMedicoId(),
                        citaGuardada.getDiagnostico(),
                        citaGuardada.getTratamiento(),
                        citaGuardada.getObservaciones()
                );
                historialCliente.registrar(historialDto);
            } catch (FeignException ex) {
                throw new ServicioNoDisponibleException(
                        "La cita fue completada pero el servicio de historial no está disponible. Intente registrar el historial manualmente.");
            }

            return citaGuardada;
        }

        // Para cualquier otro estado: solo actualizar
        cita.setEstado(dto.getNuevoEstado());
        return repositorio.save(cita);
    }

    @Override
    public void eliminar(Long id) {
        Cita cita = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));
        repositorio.delete(cita);
    }

}
