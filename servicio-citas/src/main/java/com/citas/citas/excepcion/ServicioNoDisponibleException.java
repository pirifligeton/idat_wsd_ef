package com.citas.citas.excepcion;

/**
 * Excepción lanzada cuando un microservicio externo (pacientes o médicos)
 * no puede ser alcanzado o devuelve un error durante una llamada Feign.
 * Es mapeada a HTTP 422 Unprocessable Entity por el ManejadorExcepciones.
 */
public class ServicioNoDisponibleException extends RuntimeException {

    public ServicioNoDisponibleException(String mensaje) {
        super(mensaje);
    }

}
