package com.citas.historial.dto;

/**
 * DTO recibido desde servicio-citas al completar una cita médica.
 * Contiene todos los datos necesarios para crear el registro de historial.
 */
public class HistorialDto {

    private Long citaId;
    private Long pacienteId;
    private Long medicoId;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public HistorialDto() {}

    // Getters y Setters

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

}
