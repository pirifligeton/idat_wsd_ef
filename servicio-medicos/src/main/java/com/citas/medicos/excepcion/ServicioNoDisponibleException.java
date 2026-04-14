package com.citas.medicos.excepcion;

/**
 * Excepción lanzada cuando servicio-especialidades no puede ser alcanzado
 * o devuelve un ID inexistente durante una llamada Feign.
 * Es mapeada a HTTP 422 Unprocessable Entity por el ManejadorExcepciones.
 */
public class ServicioNoDisponibleException extends RuntimeException {

    public ServicioNoDisponibleException(String mensaje) {
        super(mensaje);
    }

}
