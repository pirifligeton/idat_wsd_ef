package com.citas.citas.dto;

import com.citas.citas.entidad.EstadoCita;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO enriquecido que combina los datos de la cita con la información
 * completa del paciente (desde servicio-pacientes) y del médico (desde servicio-medicos).
 * Es retornado en el endpoint de detalle GET /api/citas/{id}.
 */
public class CitaDetalleDto {

    private Long id;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String motivo;
    private EstadoCita estado;

    // Datos del paciente obtenidos de servicio-pacientes
    private PacienteDto paciente;

    // Datos del médico obtenidos de servicio-medicos
    private MedicoDto medico;

    public CitaDetalleDto() {}

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }

    public LocalTime getHoraCita() { return horaCita; }
    public void setHoraCita(LocalTime horaCita) { this.horaCita = horaCita; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public PacienteDto getPaciente() { return paciente; }
    public void setPaciente(PacienteDto paciente) { this.paciente = paciente; }

    public MedicoDto getMedico() { return medico; }
    public void setMedico(MedicoDto medico) { this.medico = medico; }

}
