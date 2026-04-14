package com.citas.citas.dto;

import com.citas.citas.entidad.EstadoCita;

/**
 * DTO utilizado en el endpoint PATCH /api/citas/{id}/estado.
 * Cuando nuevoEstado es COMPLETADA, los campos clínicos son obligatorios.
 * Para cualquier otro estado, los campos clínicos son ignorados.
 */
public class CambioEstadoDto {

    private EstadoCita nuevoEstado;

    // Campos clínicos — requeridos únicamente cuando nuevoEstado = COMPLETADA
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public CambioEstadoDto() {}

    // Getters y Setters

    public EstadoCita getNuevoEstado() { return nuevoEstado; }
    public void setNuevoEstado(EstadoCita nuevoEstado) { this.nuevoEstado = nuevoEstado; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

}
