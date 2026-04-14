package com.citas.medicos.excepcion;

import java.time.LocalDateTime;

/**
 * DTO estándar para respuestas de error en la API.
 * Devuelto por el ManejadorExcepciones en lugar de los errores por defecto de Spring.
 */
public class RespuestaError {

    private int estado;
    private String mensaje;
    private LocalDateTime marcaTiempo;

    public RespuestaError(int estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.marcaTiempo = LocalDateTime.now();
    }

    public int getEstado() { return estado; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getMarcaTiempo() { return marcaTiempo; }

}
