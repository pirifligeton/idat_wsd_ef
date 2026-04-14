package com.citas.historial.controlador;

import com.citas.historial.dto.HistorialDto;
import com.citas.historial.entidad.HistorialMedico;
import com.citas.historial.servicio.HistorialServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el historial médico.
 * El endpoint POST es invocado exclusivamente por servicio-citas
 * al completar una cita. Los endpoints GET están disponibles para consultas.
 * No existen endpoints PUT ni DELETE — los registros son inmutables.
 */
@RestController
@RequestMapping("/api/historial")
@Tag(name = "Historial Médico", description = "Registro inmutable de resultados de citas completadas")
public class HistorialControlador {

    private final HistorialServicio servicio;

    public HistorialControlador(HistorialServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    @Operation(summary = "Registrar entrada de historial (invocado por servicio-citas al completar una cita)")
    public ResponseEntity<HistorialMedico> registrar(@RequestBody HistorialDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos los registros de historial médico")
    public ResponseEntity<List<HistorialMedico>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un registro de historial por su ID")
    public ResponseEntity<HistorialMedico> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @GetMapping("/cita/{citaId}")
    @Operation(summary = "Obtener el historial de una cita específica")
    public ResponseEntity<HistorialMedico> obtenerPorCitaId(@PathVariable Long citaId) {
        return ResponseEntity.ok(servicio.obtenerPorCitaId(citaId));
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar todo el historial médico de un paciente")
    public ResponseEntity<List<HistorialMedico>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(servicio.listarPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Listar todos los registros atendidos por un médico")
    public ResponseEntity<List<HistorialMedico>> listarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(servicio.listarPorMedico(medicoId));
    }

}
