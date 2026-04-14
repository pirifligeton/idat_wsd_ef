package com.citas.historial.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para el servicio de historial médico.
 */
@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RespuestaError(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(RecursoYaExisteException.class)
    public ResponseEntity<RespuestaError> manejarYaExiste(RecursoYaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new RespuestaError(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> manejarGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RespuestaError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error interno del servidor: " + ex.getMessage()));
    }

}
