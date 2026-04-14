package com.citas.citas.dto;

/**
 * DTO enviado desde servicio-citas hacia servicio-historial-medico
 * mediante el cliente Feign al completar una cita.
 * Transporta todos los datos necesarios para crear el registro de historial.
 */
public class HistorialDto {

    private Long citaId;
    private Long pacienteId;
    private Long medicoId;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public HistorialDto() {}

    public HistorialDto(Long citaId, Long pacienteId, Long medicoId,
                        String diagnostico, String tratamiento, String observaciones) {
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
    }

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
