package com.citas.especialidades.excepcion;

/**
 * Excepción lanzada cuando un recurso solicitado no existe en la base de datos.
 * Es mapeada a HTTP 404 por el ManejadorExcepciones.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) { super(mensaje); }
}
