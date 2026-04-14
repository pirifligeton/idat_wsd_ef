package com.citas.citas.excepcion;

/**
 * Excepción lanzada cuando un recurso solicitado no existe.
 * Es mapeada a HTTP 404 por el ManejadorExcepciones.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

}
