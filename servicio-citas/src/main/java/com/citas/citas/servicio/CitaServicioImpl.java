package com.citas.citas.servicio;

import com.citas.citas.cliente.MedicoCliente;
import com.citas.citas.cliente.PacienteCliente;
import com.citas.citas.dto.CitaDetalleDto;
import com.citas.citas.dto.CitaDto;
import com.citas.citas.dto.MedicoDto;
import com.citas.citas.dto.PacienteDto;
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
 * Se comunica con servicio-pacientes y servicio-medicos para enriquecer respuestas.
 */
@Service
public class CitaServicioImpl implements CitaServicio {

    private final CitaRepositorio repositorio;
    private final PacienteCliente pacienteCliente;
    private final MedicoCliente medicoCliente;

    public CitaServicioImpl(CitaRepositorio repositorio,
                            PacienteCliente pacienteCliente,
                            MedicoCliente medicoCliente) {
        this.repositorio = repositorio;
        this.pacienteCliente = pacienteCliente;
        this.medicoCliente = medicoCliente;
    }

    @Override
    public List<Cita> listarTodas() {
        return repositorio.findAll();
    }

    @Override
    public CitaDetalleDto obtenerDetallePorId(Long id) {
        Cita cita = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        // Llamadas a los otros microservicios para obtener datos completos
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

        // Construcción del DTO enriquecido
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
        // Validar que el paciente exista en servicio-pacientes antes de guardar
        try {
            pacienteCliente.obtenerPorId(dto.getPacienteId());
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException("No existe un paciente con id: " + dto.getPacienteId());
        } catch (FeignException ex) {
            throw new ServicioNoDisponibleException("El servicio de pacientes no está disponible en este momento.");
        }

        // Validar que el médico exista en servicio-medicos antes de guardar
        try {
            medicoCliente.obtenerPorId(dto.getMedicoId());
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException("No existe un médico con id: " + dto.getMedicoId());
        } catch (FeignException ex) {
            throw new ServicioNoDisponibleException("El servicio de médicos no está disponible en este momento.");
        }

        // Validar que no exista una cita duplicada en la misma fecha/hora con el mismo médico
        boolean existeDuplicado = repositorio.existsByPacienteIdAndMedicoIdAndFechaCitaAndHoraCita(
                dto.getPacienteId(), dto.getMedicoId(), dto.getFechaCita(), dto.getHoraCita()
        );
        if (existeDuplicado) {
            throw new RecursoYaExisteException("Ya existe una cita registrada para ese médico en esa fecha y hora.");
        }

        // Estado por defecto: PENDIENTE si no se especifica
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
    public Cita cambiarEstado(Long id, EstadoCita nuevoEstado) {
        Cita cita = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));

        cita.setEstado(nuevoEstado);
        return repositorio.save(cita);
    }

    @Override
    public void eliminar(Long id) {
        Cita cita = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id: " + id));
        repositorio.delete(cita);
    }

}
