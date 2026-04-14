package com.citas.historial.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar un historial para una cita
 * que ya tiene uno (citaId único). Mapeada a HTTP 409 Conflict.
 */
public class RecursoYaExisteException extends RuntimeException {
    public RecursoYaExisteException(String mensaje) { super(mensaje); }
}
