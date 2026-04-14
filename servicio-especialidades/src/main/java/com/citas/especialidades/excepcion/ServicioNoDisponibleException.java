package com.citas.especialidades.excepcion;

/**
 * Excepción lanzada cuando servicio-medicos no puede ser alcanzado vía Feign.
 * Es mapeada a HTTP 422 Unprocessable Entity por el ManejadorExcepciones.
 */
public class ServicioNoDisponibleException extends RuntimeException {
    public ServicioNoDisponibleException(String mensaje) { super(mensaje); }
}
