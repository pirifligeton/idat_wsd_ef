package com.citas.especialidades.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar una especialidad duplicada.
 * Es mapeada a HTTP 409 Conflict por el ManejadorExcepciones.
 */
public class RecursoYaExisteException extends RuntimeException {
    public RecursoYaExisteException(String mensaje) { super(mensaje); }
}
