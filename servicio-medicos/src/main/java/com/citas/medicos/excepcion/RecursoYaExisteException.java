package com.citas.medicos.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar un recurso que ya existe.
 * Es mapeada a HTTP 409 Conflict por el ManejadorExcepciones.
 */
public class RecursoYaExisteException extends RuntimeException {

    public RecursoYaExisteException(String mensaje) {
        super(mensaje);
    }

}
