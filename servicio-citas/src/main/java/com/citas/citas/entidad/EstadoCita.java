package com.citas.citas.entidad;

/**
 * Enum que representa los posibles estados de una cita médica.
 */
public enum EstadoCita {

    /** La cita fue agendada pero aún no confirmada */
    PENDIENTE,

    /** La cita fue confirmada por el médico o el sistema */
    CONFIRMADA,

    /** La cita fue cancelada por el paciente o el médico */
    CANCELADA,

    /** La cita se realizó exitosamente */
    COMPLETADA

}
