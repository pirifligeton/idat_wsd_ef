package com.citas.historial.entidad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa el registro de historial médico generado
 * automáticamente al completar una cita médica.
 *
 * Es un registro inmutable: una vez creado no puede ser modificado ni eliminado.
 * Los IDs de cita, paciente y médico son referencias lógicas hacia sus
 * respectivos microservicios (sin FK entre bases de datos).
 */
@Entity
@Table(name = "historial_medico")
public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cita_id", nullable = false, unique = true)
    private Long citaId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String diagnostico;

    @Column(nullable = false)
    private String tratamiento;

    // Observaciones opcionales
    @Column(nullable = true)
    private String observaciones;

    // Constructor vacío requerido por JPA
    public HistorialMedico() {}

    public HistorialMedico(Long citaId, Long pacienteId, Long medicoId,
                           String diagnostico, String tratamiento, String observaciones) {
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaRegistro = LocalDateTime.now();
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
    }

    // Getters — sin setters para reforzar la inmutabilidad del registro

    public Long getId() { return id; }
    public Long getCitaId() { return citaId; }
    public Long getPacienteId() { return pacienteId; }
    public Long getMedicoId() { return medicoId; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getObservaciones() { return observaciones; }

}
