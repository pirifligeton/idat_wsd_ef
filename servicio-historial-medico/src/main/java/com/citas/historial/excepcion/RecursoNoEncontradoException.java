package com.citas.historial.excepcion;

/**
 * Excepción lanzada cuando un registro de historial no existe.
 * Es mapeada a HTTP 404 por el ManejadorExcepciones.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) { super(mensaje); }
}
