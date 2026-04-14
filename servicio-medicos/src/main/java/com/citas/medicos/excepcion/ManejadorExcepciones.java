package com.citas.medicos.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Manejador global de excepciones para el servicio de médicos.
 * Intercepta excepciones lanzadas en los controladores y devuelve
 * respuestas HTTP estructuradas en lugar de errores genéricos de Spring.
 */
@RestControllerAdvice
public class ManejadorExcepciones {

    /**
     * Maneja recursos no encontrados → HTTP 404
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<RespuestaError> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja recursos duplicados → HTTP 409 Conflict
     */
    @ExceptionHandler(RecursoYaExisteException.class)
    public ResponseEntity<RespuestaError> manejarYaExiste(RecursoYaExisteException ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja especialidad inexistente o servicio caído → HTTP 422 Unprocessable Entity
     */
    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<RespuestaError> manejarServicioNoDisponible(ServicioNoDisponibleException ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    /**
     * Maneja cualquier otra excepción no controlada → HTTP 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> manejarGeneral(Exception ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
