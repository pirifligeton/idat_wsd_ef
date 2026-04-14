package com.citas.citas.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa una cita médica en el sistema.
 * Almacena los IDs del paciente y médico como referencias lógicas
 * hacia sus respectivos microservicios (sin FK directa entre servicios).
 *
 * Los campos clínicos (diagnostico, tratamiento, observaciones) son nulos
 * hasta que la cita es marcada como COMPLETADA mediante el endpoint PATCH.
 */
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;

    @Column(nullable = false)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado;

    // Campos clínicos — se rellenan únicamente al completar la cita
    @Column(nullable = true)
    private String diagnostico;

    @Column(nullable = true)
    private String tratamiento;

    @Column(nullable = true)
    private String observaciones;

    // Constructor vacío requerido por JPA
    public Cita() {}

    public Cita(Long pacienteId, Long medicoId, LocalDate fechaCita,
                LocalTime horaCita, String motivo, EstadoCita estado) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.motivo = motivo;
        this.estado = estado;
    }

    // Getters y Setters

    public Long getId() { return id; }

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

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

}
