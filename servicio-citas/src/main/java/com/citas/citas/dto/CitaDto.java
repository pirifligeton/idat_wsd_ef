package com.citas.citas.dto;

import com.citas.citas.entidad.EstadoCita;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO utilizado para recibir datos al crear o actualizar una cita médica.
 */
public class CitaDto {

    private Long pacienteId;
    private Long medicoId;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String motivo;
    private EstadoCita estado;

    public CitaDto() {}

    // Getters y Setters

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }

    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }

    public LocalTime getHoraCita() { return horaCita; }
    public void setHoraCita(LocalTime horaCita) { this.horaCita = horaCita; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

}
